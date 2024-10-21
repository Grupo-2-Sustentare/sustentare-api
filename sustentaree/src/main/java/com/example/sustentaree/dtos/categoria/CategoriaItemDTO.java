package com.example.sustentaree.dtos.categoria;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

public class CategoriaItemDTO {

  private Integer id;

  @NotBlank
  private String nome;

  private Boolean ativo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }
}
