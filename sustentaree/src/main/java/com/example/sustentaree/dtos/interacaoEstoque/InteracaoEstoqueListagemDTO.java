package com.example.sustentaree.dtos.interacaoEstoque;

import com.example.sustentaree.domain.produto.Produto;

import java.time.LocalDateTime;

public class InteracaoEstoqueListagemDTO {
  private Integer id;

  private Produto produto;

  private LocalDateTime dataHora;

  private String categoriaInteracao;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }

  public String getCategoriaInteracao() {
    return categoriaInteracao;
  }

  public void setCategoriaInteracao(String categoriaInteracao) {
    this.categoriaInteracao = categoriaInteracao;
  }
}
