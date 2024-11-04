package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.exception.EntidadeNaoEncontradaException;
import com.example.sustentaree.exception.EstoqueInsuficienteException;
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
      Produto novoProduto,
      Integer fkItem,
      Integer idResponsavel
  ) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    Produto ultimoProduto = produtoService.getByItemIdAndAtivo(fkItem);
//    Se tiver ultimo produto, calcula a quantidade total com base na categoria de interação
//    ( Se for algum tipo de ENTRADA, SOMA, se for QUALQUER COISA SUBTRAI)
    if (ultimoProduto != null) {
      if (novaInteracao.getCategoriaInteracao().equals("Entrada") || novaInteracao.getCategoriaInteracao().equals("Compra de última hora")) {
        novoProduto.setQtdProdutoTotal(ultimoProduto.getQtdProdutoTotal() + novoProduto.getQtdProduto());
      } else {
//        Se for saída, verifica se tem estoque suficiente para realizar a subtração
        if (ultimoProduto.getQtdProdutoTotal() < novoProduto.getQtdProduto()) {
          throw new EstoqueInsuficienteException("Estoque insuficiente para realizar a subtração.");
        }
        novoProduto.setQtdProdutoTotal(ultimoProduto.getQtdProdutoTotal() - novoProduto.getQtdProduto());
      }
    }
    else {
//    Se não tiver, seta a quantidade total como a quantidade
      novoProduto.setQtdProdutoTotal(novoProduto.getQtdProduto());
  }
//    Cria o produto e o adiciona a essa interação
    Produto produtoCriado = this.produtoService.criar(novoProduto, fkItem, idResponsavel);
    novaInteracao.setProduto(produtoCriado);
    return this.repository.save(novaInteracao);
  }
  public String gerarCsv() {
    List<InteracaoEstoque> interacaoes = this.listar();
    StringJoiner csv = new StringJoiner("\n");
    csv.add("id;produto;dataHora;categoriaInteracao");

    for (InteracaoEstoque interacaoEstoque : interacaoes) {
      StringJoiner linha = new StringJoiner(";");
      linha.add(interacaoEstoque.getId().toString());
      linha.add(interacaoEstoque.getProduto().getItem().getNome());
      linha.add(interacaoEstoque.getDataHora().toString());
      linha.add(interacaoEstoque.getCategoriaInteracao());

      csv.add(linha.toString());
    }

    return csv.toString();
  }

  public InteracaoEstoque kpiUltimaAdicaoEstoque() {
    return repository.findByUltimaAdicao();
  }

  public List<InteracaoEstoque> kpiMaiorRetirada(){
    return repository.getMaiorRetirada();
  }


}
