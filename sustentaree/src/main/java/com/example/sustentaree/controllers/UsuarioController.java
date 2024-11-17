package com.example.sustentaree.controllers;

import com.example.sustentaree.controllers.autenticacao.dto.UsuarioLoginDto;
import com.example.sustentaree.controllers.autenticacao.dto.UsuarioTokenDto;
import com.example.sustentaree.dtos.usuario.UsuarioSemImagemDTO;
import com.example.sustentaree.repositories.ItemRepository;
import com.example.sustentaree.services.FileService;
import com.example.sustentaree.services.LambdaService;
import com.example.sustentaree.services.ImagemService;
import com.example.sustentaree.services.UsuarioService;
import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.dtos.usuario.AlterarUsuarioDTO;
import com.example.sustentaree.dtos.usuario.UsuarioDTO;
import com.example.sustentaree.mapper.UsuarioMapper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

  @Autowired
  private final UsuarioService service;
  @Autowired
  private LambdaService lambdaService;
  @Autowired
  FileService fileService;
  @Autowired
  private ItemRepository itemRepository;
  @Autowired
  private ImagemService imagemService;

  public UsuarioController(UsuarioService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<UsuarioDTO> criar(@RequestBody @Valid UsuarioDTO dto, @RequestParam int idResponsavel) throws IOException {
      dto.setAtivo(true);
    UsuarioMapper mapper = UsuarioMapper.INSTANCE;

    if (dto.getImagem() != null){
        CompletableFuture.runAsync(() ->
                {
                    byte[] imagemBytes = Base64.getDecoder().decode(dto.getImagem());
                    Integer idUsuario = service.getUltimoId() + 1;
                    String nomeArquivo = "/usuarios/imagens/"+idUsuario.toString();
                    lambdaService.enviarImagemS3(imagemBytes, nomeArquivo, "envioDeImagem");
                }
                );
    }


    Usuario entity = mapper.toUsuario(dto);
    Usuario usuarioSalvo = this.service.criar(entity, idResponsavel);

    UsuarioDTO response =  mapper.toUsuarioDTO(usuarioSalvo);
    if (dto.getImagem() != null){
        response.setImagem(imagemService.convertToJPEG(Base64.getDecoder().decode(dto.getImagem()),1));
    }

    return ResponseEntity.created(null).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){
    UsuarioTokenDto usuarioToken = this.service.autenticar(usuarioLoginDto);

    return ResponseEntity.status(200).body(usuarioToken);
  }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<Usuario> usuarios = this.service.listar();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<UsuarioDTO> response = imagemService.addImagensS3Usuarios(usuarios);

        return ResponseEntity.ok(response);
    }

    @GetMapping("listar-sem-imagem")
    public ResponseEntity<List<UsuarioSemImagemDTO>> listarSemImagem() {
        List<Usuario> usuarios = this.service.listar();

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        UsuarioMapper mapper = UsuarioMapper.INSTANCE;
        List<UsuarioSemImagemDTO> response = mapper.toUsuarioSemImagemDTOList(usuarios);

        return ResponseEntity.ok(response);
    }

    private String convertToJPEG(byte[] imageBytes, float quality) throws IOException {
        // Converte o byte array para BufferedImage
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
        if (image == null) {
            throw new IOException("Image could not be decoded");
        }

        // Cria uma nova imagem em RGB para garantir o formato JPEG
        BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        rgbImage.getGraphics().drawImage(image, 0, 0, null);

        // Prepara o fluxo de saída para a imagem comprimida
        ByteArrayOutputStream compressedOutput = new ByteArrayOutputStream();

        // Obtém o ImageWriter para o formato JPEG
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IOException("No writers found for JPEG format");
        }

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();
        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(quality); // Define a qualidade (0 a 1)

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(compressedOutput)) {
            writer.setOutput(ios);
            writer.write(null, new javax.imageio.IIOImage(rgbImage, null, null), param);
        } finally {
            writer.dispose();
        }

        // Converte a imagem comprimida de volta para base64
        return Base64.getEncoder().encodeToString(compressedOutput.toByteArray());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
      Usuario usuario = this.service.porId(id);
      if (usuario == null){
          return ResponseEntity.notFound().build();
      }
      UsuarioDTO usuarioDTO = imagemService.addImagemS3Usuario(usuario);
      return ResponseEntity.ok(usuarioDTO);
    }

  @PatchMapping("/{id}")
  public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AlterarUsuarioDTO dto, @RequestParam int idResponsavel) {

      if (dto.getImagem() != null){
          CompletableFuture.runAsync(() ->
                  {
                      byte[] imagemBytes = Base64.getDecoder().decode(dto.getImagem());
                      String nomeArquivo = "/usuarios/imagens/"+id.toString();
                      lambdaService.enviarImagemS3(imagemBytes, nomeArquivo, "envioDeImagem");
                  }
          );
      }

      UsuarioMapper mapper = UsuarioMapper.INSTANCE;

    Usuario entity = mapper.toUsuario(dto);
    Usuario usuarioAtualizado = this.service.atualizar(entity, id, idResponsavel);

    UsuarioDTO response = mapper.toUsuarioDTO(usuarioAtualizado);
    return ResponseEntity.ok(response);
  }

//    TODO - Implementar validação de que o idSolicitante é igual ao id do usuário logado no front
//    TODO - Pensar em uma maneira de não precisar da validação front-end para garantir a segurança do método.

  @PutMapping("/{id}")
  public ResponseEntity<UsuarioDTO> atualizarOutros(@RequestParam int idSolicitante, @PathVariable Integer id, @RequestBody @Valid UsuarioDTO dto, @RequestParam int idResponsavel) {
    UsuarioMapper mapper = UsuarioMapper.INSTANCE;

    Usuario entity = mapper.toUsuario(dto);
    Usuario usuarioAtualizado = this.service.atualizarOutros(idSolicitante, entity, id, idResponsavel);
      if (dto.getImagem() != null){
          CompletableFuture.runAsync(() ->
                  {
                      byte[] imagemBytes = Base64.getDecoder().decode(dto.getImagem());
                      String nomeArquivo = "/usuarios/imagens/"+id.toString();
                      lambdaService.enviarImagemS3(imagemBytes, nomeArquivo, "envioDeImagem");
                  }
          );
      }

    UsuarioDTO response = mapper.toUsuarioDTO(usuarioAtualizado);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Integer id, @RequestParam int idResponsavel) {
    this.service.deletar(id, idResponsavel);

    return ResponseEntity.noContent().build();
  }

    @GetMapping("/usuario-ultimo-id")
    public ResponseEntity<UsuarioDTO> getUltimoId(){
        Integer ultimoIdAdicionado = service.getUltimoId();
        Usuario usuario = service.porId(ultimoIdAdicionado);
        UsuarioDTO usuarioDTO = imagemService.addImagemS3Usuario(usuario);
        System.out.println("------------------------------------------------");
        System.out.println("Ultimo ID adicionado: " + usuarioDTO.getNome() + " - " + usuarioDTO.getId() + " - " + usuarioDTO.getImagem());
        System.out.println("-----------------Ultimo ID-----------------------");
        return ResponseEntity.ok(usuarioDTO);
    }

}

