package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.dtos.produto.AlterarProdutoDTO;
import com.example.sustentaree.dtos.produto.ProdutoCriacaoDTO;
import com.example.sustentaree.dtos.produto.ProdutoListagemDTO;
import com.example.sustentaree.dtos.usuario.UsuarioDTO;
import com.example.sustentaree.mapper.ProdutoMapper;
import com.example.sustentaree.mapper.UsuarioMapper;
import com.example.sustentaree.services.LambdaService;
import com.example.sustentaree.services.ProdutoService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
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
import java.net.URI;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

  @Autowired
  private final ProdutoService service;
  @Autowired
  private LambdaService lambdaService;

  public ProdutoController(ProdutoService service) {
    this.service = service;
  }

  @PostMapping
  @Transactional(rollbackOn = Exception.class)
  public ResponseEntity<ProdutoListagemDTO> criar(@RequestBody @Valid ProdutoCriacaoDTO produtoCriacaoDTO, @RequestParam int fkItem, @RequestParam int idResponsavel) {

    if (produtoCriacaoDTO.getImagem() != null) {
      CompletableFuture.runAsync(() ->
              {
                byte[] imagemBytes = Base64.getDecoder().decode(produtoCriacaoDTO.getImagem());
                Integer idProduto = service.getUltimoId() + 1;
                String nomeArquivo = "/produtos/imagens/" + idProduto.toString();
                lambdaService.enviarImagemS3(imagemBytes, nomeArquivo, "envioDeImagem");
              }
      );
    }

    ProdutoMapper mapper = ProdutoMapper.INSTANCE;
    Produto produto = mapper.toProduto(produtoCriacaoDTO);
    produto.setAtivo(true);
    Produto produtoCriado = this.service.criar(produto, fkItem, idResponsavel);

    URI uri = URI.create("/produtos/" + produtoCriado.getId());
    return ResponseEntity.created(uri).body(ProdutoMapper.INSTANCE.toProdutoListagemDTO(produto));
  }

  @GetMapping
  public ResponseEntity<List<ProdutoListagemDTO>> listar() {
    List<Produto> produtos = this.service.listar();

    if (produtos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }


    ProdutoMapper mapper = ProdutoMapper.INSTANCE;
    List<ProdutoListagemDTO> response = mapper.toProdutoListagemDTO(produtos);

    int contador = 0;
    for (ProdutoListagemDTO produto : response) {
      try {
        byte[] imagem = lambdaService.downloadFile("sustentaree-s3", "/produtos/imagens/" + produtos.get(contador).getId().toString());
        response.get(contador).setImagem(convertToJPEG(imagem, 1));
      } catch (Exception e) {
        System.out.println(e);
      }

      contador++;
    }


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
  public ResponseEntity<ProdutoListagemDTO> buscarPoId(@PathVariable Integer id){
    Produto produto = this.service.porId(id);

    ProdutoMapper mapper = ProdutoMapper.INSTANCE;
    ProdutoListagemDTO response = mapper.toProdutoListagemDTO(produto);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ProdutoListagemDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AlterarProdutoDTO alterarProdutoDTO, @RequestParam int fkItem ,@RequestParam int idResponsavel){
    ProdutoMapper mapper = ProdutoMapper.INSTANCE;
    Produto produto = mapper.toProdutoUpdate(alterarProdutoDTO);

    Produto produtoAtualizado = this.service.Atualizar(produto,id, fkItem, idResponsavel);

    ProdutoListagemDTO response = mapper.toProdutoListagemDTO(produtoAtualizado);
    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remover(@PathVariable Integer id, @RequestParam int idResponsavel) {
    this.service.deletar(id, idResponsavel);
    return ResponseEntity.notFound().build();
  }

  @GetMapping("/hash/{categoria}")
  public ResponseEntity<List<Produto>> getByHash(@PathVariable String categoria, @RequestParam int idResponsavel){
    return ResponseEntity.ok(this.service.getByHash(categoria, idResponsavel));
  }
}
