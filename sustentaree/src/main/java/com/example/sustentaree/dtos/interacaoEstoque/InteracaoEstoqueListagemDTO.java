package com.example.sustentaree.dtos.interacaoEstoque;

import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.domain.produto.Produto;
import lombok.Data;

import java.time.LocalDateTime;

public class InteracaoEstoqueListagemDTO {
  private Integer id;

  // Ajustar o fkProduto.
  private Produto produto;

  private LocalDateTime dataHora;

  //    0 ou 1 fechamento estoque para cada interação do estoque
  private Fechamento fechamento;

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

  public Fechamento getFechamento() {
    return fechamento;
  }

  public void setFechamento(Fechamento fechamento) {
    this.fechamento = fechamento;
  }

  public String getCategoriaInteracao() {
    return categoriaInteracao;
  }

  public void setCategoriaInteracao(String categoriaInteracao) {
    this.categoriaInteracao = categoriaInteracao;
  }
}
