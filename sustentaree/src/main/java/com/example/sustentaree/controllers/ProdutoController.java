package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.dtos.produto.AlterarProdutoDTO;
import com.example.sustentaree.dtos.produto.ProdutoCriacaoDTO;
import com.example.sustentaree.dtos.produto.ProdutoListagemDTO;
import com.example.sustentaree.mapper.ProdutoMapper;
import com.example.sustentaree.services.LambdaService;
import com.example.sustentaree.services.ProdutoService;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Base64;
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
  public ResponseEntity<ProdutoListagemDTO> criar(@RequestBody @Valid ProdutoCriacaoDTO produtoCriacaoDTO, @RequestParam int fkItem, @RequestParam int idResponsavel){

    if (produtoCriacaoDTO.getImagem() != null){
      CompletableFuture.runAsync(() ->
              {
                byte[] imagemBytes = Base64.getDecoder().decode(produtoCriacaoDTO.getImagem());
                Integer idProduto = service.getUltimoId() + 1;
                String nomeArquivo = "/produtos/imagens/"+idProduto.toString();
                lambdaService.enviarImagemS3(imagemBytes, nomeArquivo, "envioDeImagem");
              }
      );
    }

    ProdutoMapper mapper = ProdutoMapper.INSTANCE;
    Produto produto = mapper.toProduto(produtoCriacaoDTO);
    Produto produtoCriado = this.service.criar(produto, fkItem, idResponsavel);

    URI uri = URI.create("/produtos/" + produtoCriado.getId());
    return ResponseEntity.created(uri).body(ProdutoMapper.INSTANCE.toProdutoListagemDTO(produto));
  }

  @GetMapping
  public ResponseEntity<List<ProdutoListagemDTO>> listar(){
    List<Produto> produtos = this.service.listar();

    ProdutoMapper mapper = ProdutoMapper.INSTANCE;
    List<ProdutoListagemDTO> response = mapper.toProdutoListDTO(produtos);
    return ResponseEntity.ok(response);
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
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/hash/{categoria}")
  public ResponseEntity<List<Produto>> getByHash(@PathVariable String categoria, @RequestParam int idResponsavel){
    return ResponseEntity.ok(this.service.getByHash(categoria, idResponsavel));
  }

  @GetMapping("/categoria/{id}")
  public ResponseEntity<List<ProdutoListagemDTO>> listarPorCategoria(@PathVariable int id){
    List<Produto> produtos = this.service.listarPorItem(id);
    ProdutoMapper mapper = ProdutoMapper.INSTANCE;
    List<ProdutoListagemDTO> response = mapper.toProdutoListDTO(produtos);
    return ResponseEntity.ok(response);
  }
  @GetMapping("/categorias")
  public ResponseEntity<List<ProdutoListagemDTO>> listarPorCategorias(@RequestParam List<Integer> ids){
    List<Produto> produtos = this.service.listarPorItens(ids);
    ProdutoMapper mapper = ProdutoMapper.INSTANCE;
    List<ProdutoListagemDTO> response = mapper.toProdutoListDTO(produtos);
    return ResponseEntity.ok(response);
  }
}
