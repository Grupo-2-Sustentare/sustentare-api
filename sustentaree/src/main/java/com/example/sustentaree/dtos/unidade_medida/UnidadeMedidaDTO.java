package com.example.sustentaree.dtos.unidade_medida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UnidadeMedidaDTO {

  @NotBlank
  private String categoria;
  @NotNull
  private Double conversao_padrao;
  @NotNull
  private String nome;
  @NotNull
  private String simbolo;
  private Boolean ativo;

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public Double getConversao_padrao() {
    return conversao_padrao;
  }

  public void setConversao_padrao(Double conversao_padrao) {
    this.conversao_padrao = conversao_padrao;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSimbolo() {
    return simbolo;
  }

  public void setSimbolo(String simbolo) {
    this.simbolo = simbolo;
  }
}
