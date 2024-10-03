package com.example.sustentaree.dtos.item;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public class ItemCriacaoDTO {
  @NotBlank
  private String nome;
  @NotNull
  private Boolean perecivel;
  private Integer dias_vencimento;
  @AssertTrue
  private Boolean ativo;

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
}
