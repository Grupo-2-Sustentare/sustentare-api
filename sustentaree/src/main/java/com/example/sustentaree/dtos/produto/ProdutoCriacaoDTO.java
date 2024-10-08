package com.example.sustentaree.dtos.produto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

public class ProdutoCriacaoDTO {

  // @ManyToOne -> trazer o id do Item correspondente ao produto
  @NotNull
  @Positive
  private Double preco;
  @NotNull
  @Positive
  private Integer qtdProduto;
  @NotNull
  @Positive
  private Double qtdMedida;

  private String imagem;

  @AssertTrue
  private Boolean ativo;

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

