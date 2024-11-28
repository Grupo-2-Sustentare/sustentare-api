package com.example.sustentaree.dtos.item;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;


public class ItemCriacaoDTO {
  @NotBlank
  private String nome;
  @NotNull
  private Boolean perecivel;
  private Integer dias_vencimento;
  @PositiveOrZero
  private Double qtd_min_item;
  @AssertTrue
  private Boolean ativo;
  private String imagem;


  public String getImagem() {
    return imagem;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Boolean getPerecivel() {
    return perecivel;
  }

  public void setPerecivel(Boolean perecivel) {
    this.perecivel = perecivel;
  }

  public Integer getDias_vencimento() {
    return dias_vencimento;
  }

  public void setDias_vencimento(Integer dias_vencimento) {
    this.dias_vencimento = dias_vencimento;
  }

  public @NotNull @PositiveOrZero Double getQtd_min_item() {
    return qtd_min_item;
  }

  public void setQtd_min_item(@NotNull @PositiveOrZero Double qtd_min_item) {
    this.qtd_min_item = qtd_min_item;
  }
}
