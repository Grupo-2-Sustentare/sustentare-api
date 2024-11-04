package com.example.sustentaree.dtos.produto;

import jakarta.validation.constraints.*;
import lombok.Data;

public class ProdutoCriacaoDTO {

  // @ManyToOne -> trazer o id do Item correspondente ao produto
  @NotNull
  @PositiveOrZero
  private Double preco;
  @NotNull
  @PositiveOrZero
  private Integer qtdProduto;
  @PositiveOrZero
  private Integer qtdProdutoTotal;
  @NotNull
  @PositiveOrZero
  private Double qtdMedida;

  private String imagem;

  @AssertTrue
  private Boolean ativo;

  public Integer getQtdProdutoTotal() {
    return qtdProdutoTotal;
  }

  public void setQtdProdutoTotal(Integer qtdProdutoTotal) {
    this.qtdProdutoTotal = qtdProdutoTotal;
  }

  public Boolean getAtivo() {
    return ativo;

  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public Integer getQtdProduto() {
    return qtdProduto;
  }

  public void setQtdProduto(Integer qtdProduto) {
    this.qtdProduto = qtdProduto;
  }

  public Double getQtdMedida() {
    return qtdMedida;
  }

  public void setQtdMedida(Double qtdMedida) {
    this.qtdMedida = qtdMedida;
  }

  public String getImagem() {
    return imagem;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

}

