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
      Integer fkProduto,
      Integer fkFechamento,
      Integer idResponsavel
  ) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

   if (fkFechamento == 0){
     Produto produto = this.produtoService.porId(fkProduto);
     novaInteracao.setProduto(produto);
   }else {
     Produto produto = this.produtoService.porId(fkProduto);
     novaInteracao.setProduto(produto);
     Fechamento fechamento = this.fechamentoService.porId(fkFechamento);
     novaInteracao.setFechamentoEstoque(fechamento);
   }

    return this.repository.save(novaInteracao);
  }

//  @Transactional
//  public void deletar(Integer id, int idResponsavel) {
//    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
//
//    InteracaoEstoque interacaoEstoque = this.porId(id);
//    this.repository.delete(interacaoEstoque);
//  }

  public InteracaoEstoque kpiUltimaAdicaoEstoque() {
    InteracaoEstoque interacaoEstoque = repository.findByUltimaAdicao();
    return interacaoEstoque;
  }


}
