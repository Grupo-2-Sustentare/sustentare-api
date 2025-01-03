package com.example.sustentaree.services;

import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.domain.usuario.Usuario;
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
  private final ProdutoService produtoService;

  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private final AuditService auditService;

  public InteracaoService(InteracaoRepository repository, ProdutoService produtoService, AuditService auditService) {
    this.repository = repository;
    this.produtoService = produtoService;
      this.auditService = auditService;
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
    String descricaoAudit = "";
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
        if ((ultimoProduto.getQtdProdutoTotal() - novoProduto.getQtdProduto()) <= ultimoProduto.getItem().getQtd_min_item()) {
          List<String> emails = usuarioService.listar().stream().map(Usuario::getEmail).toList();
          emailService.sendEmail(emails, "Estoque baixo", "O estoque do item " + ultimoProduto.getItem().getNome() + " está baixo, com apenas " + (ultimoProduto.getQtdProdutoTotal() - novoProduto.getQtdProduto()) + " unidades.", "Por favor, reponha o estoque.", "Equipe Sustentare", null);
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
    descricaoAudit = novaInteracao.getCategoriaInteracao();
    InteracaoEstoque interacaoEstoqueSalva = this.repository.save(novaInteracao);
    auditService.logInteracaoEstoqueAudit(descricaoAudit + " - qtd movimentada: " + novoProduto.getQtdProduto(), idResponsavel, interacaoEstoqueSalva.getId(), novoProduto.getId());
    return interacaoEstoqueSalva;
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
