package com.example.sustentaree.dtos.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

public class AlterarProdutoDTO {

  // @ManyToOne -> trazer o id do Item correspondente ao produto

  @NotNull
  @Positive
  private Double preco;
  @NotNull
  @PositiveOrZero
  private Double qtdProduto;
  @NotNull
  @Positive
  private Double qtdMedida;
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

  public @NotNull @PositiveOrZero Double getQtdProduto() {
    return qtdProduto;
  }

  public void setQtdProduto(@NotNull @PositiveOrZero Double qtdProduto) {
    this.qtdProduto = qtdProduto;
  }

  public Double getQtdMedida() {
    return qtdMedida;
  }

  public void setQtdMedida(Double qtdMedida) {
    this.qtdMedida = qtdMedida;
  }
}
