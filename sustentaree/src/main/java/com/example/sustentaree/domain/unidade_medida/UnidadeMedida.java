package com.example.sustentaree.domain.unidade_medida;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
public class UnidadeMedida {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_unidade_medida")
  private Integer id;
  @Schema(description = "Categoria da unidade de medida", example = "Massa")
  @Column(name = "categoria")
  private String categoria;
  @Schema(description = "Conversão padrão da unidade de medida", example = "1.0")
  @Column(name = "conversao_padrao")
  private Double conversao_padrao;
  @Schema(description = "Nome da unidade de medida", example = "Quilograma")
  @Column(name = "nome")
  private String nome;
  @Schema(description = "Símbolo da unidade de medida", example = "kg")
  @Column(name = "simbolo")
  private String simbolo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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
