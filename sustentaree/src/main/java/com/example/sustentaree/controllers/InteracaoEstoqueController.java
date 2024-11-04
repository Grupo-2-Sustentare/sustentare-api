package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueComProdutoDTO;
import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueListagemDTO;
import com.example.sustentaree.mapper.InteracaoEstoqueMapper;
import com.example.sustentaree.mapper.ProdutoMapper;
import com.example.sustentaree.services.InteracaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interacoes-estoque")
public class InteracaoEstoqueController {

  @Autowired
  private final InteracaoService service;

  public InteracaoEstoqueController(InteracaoService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<InteracaoEstoqueListagemDTO> criar(
        @RequestBody @Valid InteracaoEstoqueComProdutoDTO interacaoEstoqueCriacaoDTO,
        @RequestParam int fkItem,
        @RequestParam int idResponsavel
  ) {
    ProdutoMapper produtoMapper = ProdutoMapper.INSTANCE;
    InteracaoEstoqueMapper mapper = InteracaoEstoqueMapper.INSTANCE;

    Produto produto = produtoMapper.toProduto(interacaoEstoqueCriacaoDTO.getProdutoCriacaoDTO());
    InteracaoEstoque interacaoEstoque = mapper.toInteracaoEstoque(interacaoEstoqueCriacaoDTO.getInteracaoEstoqueCriacaoDTO());
    InteracaoEstoque interacaoEstoqueNovo = this.service.criar(interacaoEstoque, produto, fkItem, idResponsavel);
    InteracaoEstoqueListagemDTO response = mapper.toInteracaoEstoqueListagemDTO(interacaoEstoqueNovo);

    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<InteracaoEstoqueListagemDTO>> listar() {
    List<InteracaoEstoque> interacaoEstoques = this.service.listar();

    InteracaoEstoqueMapper mapper = InteracaoEstoqueMapper.INSTANCE;
    return ResponseEntity.ok(mapper.toInteracaoEstoqueList(interacaoEstoques));
  }

  @GetMapping("/{id}")
  public ResponseEntity<InteracaoEstoqueListagemDTO> buscarPoId(@PathVariable int id) {
    InteracaoEstoque interacaoEstoque = this.service.porId(id);

    InteracaoEstoqueMapper mapper = InteracaoEstoqueMapper.INSTANCE;
    return ResponseEntity.ok(mapper.toInteracaoEstoqueListagemDTO(interacaoEstoque));
  }

  @GetMapping("/csv")
  public ResponseEntity<String> gerarCsv() {
    {
      String csv = this.service.gerarCsv();
      HttpHeaders headers = new HttpHeaders();
      headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=interacoes_de_estoque.csv");
      headers.setContentType(MediaType.TEXT_PLAIN);

      return ResponseEntity.ok()
            .headers(headers)
            .body(csv);
    }
  }

}