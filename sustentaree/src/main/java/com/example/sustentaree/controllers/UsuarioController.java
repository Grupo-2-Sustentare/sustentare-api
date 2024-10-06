package com.example.sustentaree.controllers;

import com.example.sustentaree.controllers.autenticacao.dto.UsuarioLoginDto;
import com.example.sustentaree.controllers.autenticacao.dto.UsuarioTokenDto;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.repositories.ItemRepository;
import com.example.sustentaree.services.FileService;
import com.example.sustentaree.services.LambdaService;
import com.example.sustentaree.services.UsuarioService;
import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.dtos.usuario.AlterarUsuarioDTO;
import com.example.sustentaree.dtos.usuario.UsuarioDTO;
import com.example.sustentaree.mapper.UsuarioMapper;
import com.example.sustentaree.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Base64;
import java.util.List;
import java.util.Optional;
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

  public UsuarioController(UsuarioService service) {
    this.service = service;
  }

  @PostMapping("/gravarTxt")
  public ResponseEntity gravarTxt(){
    fileService.writeProductToFile();
    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @Operation(summary = "Criar um usuário", description = "Cria um usuário com base nas informações fornecidas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      ))
  })

  @PostMapping
  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity<UsuarioDTO> criar(@RequestBody @Valid UsuarioDTO dto, @RequestParam int idResponsavel) {
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
    return ResponseEntity.created(null).body(response);
  }

  @Operation(summary = "Login de usuário", description = "Realiza o login de um usuário com base nas informações fornecidas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário logado com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioTokenDto.class)
      )),
      @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioTokenDto.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioTokenDto.class)
      ))
  })

  @PostMapping("/login")
  public ResponseEntity<UsuarioTokenDto> login(@RequestBody UsuarioLoginDto usuarioLoginDto){
    UsuarioTokenDto usuarioToken = this.service.autenticar(usuarioLoginDto);

    return ResponseEntity.status(200).body(usuarioToken);
  }

  @Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      ))
  })

  @GetMapping
  public ResponseEntity<List<UsuarioDTO>> listar() {
    List<Usuario> usuarios = this.service.listar();

    if (usuarios.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    UsuarioMapper mapper = UsuarioMapper.INSTANCE;
    List<UsuarioDTO> response = mapper.toUsuarioListDTO(usuarios);
    int contador = 0;
      for (UsuarioDTO usuario : response) {
          try {
              byte[] imagem = lambdaService.downloadFile("sustentaree-s3", "/usuarios/imagens/"+usuarios.get(contador).getId().toString());
              response.get(contador).setImagem(Base64.getEncoder().encodeToString(imagem));
          }catch (Exception e){
              System.out.println(e);
          }

          contador++;
      }
    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Buscar um usuário por ID", description = "Retorna um usuário com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class),
          examples = @ExampleObject(
              value = "{\n  \"nome\": \"TESTE FODA Exemplo\",\n  \"senha\": \"123456\",\n  \"acesso\": 1\n}"
          )
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      ))
  })

  @GetMapping("/{id}")
  public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
    Usuario usuario = this.service.porId(id);

    byte[] imagem = lambdaService.downloadFile("sustentaree-s3", "/usuarios/imagens/"+id.toString());

    UsuarioMapper mapper = UsuarioMapper.INSTANCE;
    UsuarioDTO response = mapper.toUsuarioDTO(usuario);
    response.setImagem(Base64.getEncoder().encodeToString(imagem));

    return ResponseEntity.ok(response);
  }

  @Operation(summary = "Atualizar um usuário", description = "Atualiza um usuário com base nas informações fornecidas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      ))
  })

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

  @Operation(summary = "Alterar um usuário", description = "Altera um usuário com base nas informações fornecidas")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Usuário alterado com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      ))
  })

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

  @Operation(summary = "Remover um usuário", description = "Remove um usuário com base no ID fornecido")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      )),
      @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
          mediaType = "application/json",
          schema = @Schema(implementation = UsuarioDTO.class)
      ))
  })

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> excluir(@PathVariable Integer id, @RequestParam int idResponsavel) {
    this.service.deletar(id, idResponsavel);

    return ResponseEntity.noContent().build();
  }

}

