package com.example.sustentaree.domain.categoria;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class CategoriaItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_categoria_item")
  private Integer id;
  @Schema(description = "Nome da categoria", example = "Alimentos")
  private String nome;
  private Boolean ativo;

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

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

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("CategoriaItem{");
    sb.append("id=").append(id);
    sb.append(", nome='").append(nome).append('\'');
    sb.append(", ativo=").append(ativo);
    sb.append('}');
    return sb.toString();
  }
}
