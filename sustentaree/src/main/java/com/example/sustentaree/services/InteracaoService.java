package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.exception.EntidadeNaoEncontradaException;
import com.example.sustentaree.repositories.InteracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class InteracaoService {
  @Autowired
  private final InteracaoRepository repository;

  @Autowired
  private final FechamentoService fechamentoService;

  @Autowired
  private final ProdutoService produtoService;

  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;

  public InteracaoService(InteracaoRepository repository, FechamentoService fechamentoService, ProdutoService produtoService) {
    this.repository = repository;
    this.fechamentoService = fechamentoService;
    this.produtoService = produtoService;
  }

  public List<InteracaoEstoque> listar() {
    return this.repository.findAll();
  }
  public InteracaoEstoque porId(Integer id) {
    Optional<InteracaoEstoque> interacaoEstoqueOpt =
        repository.findById(id);

    if (interacaoEstoqueOpt.isEmpty()) {
      throw new EntidadeNaoEncontradaException("Interação de Estoque");
    }

    return interacaoEstoqueOpt.get();
  }

  @Transactional
  public InteracaoEstoque criar(
      InteracaoEstoque novaInteracao,
      int fkProduto,
      int fkFechamento,
      int idResponsavel
  ) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    Produto produto = this.produtoService.porId(fkProduto);
    Fechamento fechamento = this.fechamentoService.porId(fkFechamento);
    novaInteracao.setFkProduto(produto.getId());
    novaInteracao.setFkFechamentoEstoque(fechamento.getId());

    return this.repository.save(novaInteracao);
  }

  @Transactional
  public void deletar(Integer id, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    InteracaoEstoque interacaoEstoque = this.porId(id);
    this.repository.delete(interacaoEstoque);
  }
}
