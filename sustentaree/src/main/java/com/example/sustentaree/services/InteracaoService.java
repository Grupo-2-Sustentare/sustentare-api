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
import java.util.StringJoiner;

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
  @Transactional
  public String gerarCsv(int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    List<InteracaoEstoque> interacaoes = this.listar();
    StringJoiner csv = new StringJoiner("\n");
    csv.add("id;produto;dataHora;fechamentoEstoque;categoriaInteracao");

    for (InteracaoEstoque interacaoEstoque : interacaoes) {
      StringJoiner linha = new StringJoiner(";");
      linha.add(interacaoEstoque.getId().toString());
      linha.add(interacaoEstoque.getProduto().getItem().getNome());
      linha.add(interacaoEstoque.getDataHora().toString());
      linha.add(interacaoEstoque.getFechamentoEstoque().getDataFim().toString());
      linha.add(interacaoEstoque.getCategoriaInteracao());

      csv.add(linha.toString());
    }

    return csv.toString();
  }

//  @Transactional
//  public void deletar(Integer id, int idResponsavel) {
//    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
//
//    InteracaoEstoque interacaoEstoque = this.porId(id);
//    this.repository.delete(interacaoEstoque);
//  }

  public InteracaoEstoque kpiUltimaAdicaoEstoque() {
    return repository.findByUltimaAdicao();
  }

  public List<InteracaoEstoque> kpiMaiorRetirada(){
    return repository.getMaiorRetirada();
  }


}
