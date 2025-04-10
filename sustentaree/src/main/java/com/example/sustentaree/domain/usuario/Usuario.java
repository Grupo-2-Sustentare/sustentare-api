package com.example.sustentaree.domain.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuario")
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Integer id;
  @Schema(description = "Nome do usuário", example = "João")
  private String nome;
  @Schema(description = "Email do usuário", example = "joao@email.com")
  private String email;
  @Schema(description = "Senha do usuário", example = "123456")
  private String senha;
  @Schema(description = "Nível de acesso do usuário", example = "1")
  private Integer acesso;
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Integer getAcesso() {
    return acesso;
  }

  public void setAcesso(Integer acesso) {
    this.acesso = acesso;
  }
}
