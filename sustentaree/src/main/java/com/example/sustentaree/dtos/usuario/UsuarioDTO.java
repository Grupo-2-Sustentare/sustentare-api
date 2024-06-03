package com.example.sustentaree.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class UsuarioDTO {

  @NotBlank
  private String nome;
  @NotBlank
  private String senha;
  @NotBlank
  private String email;
  @NotNull
  private Integer acesso;

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getAcesso() {
    return acesso;
  }

  public void setAcesso(Integer acesso) {
    this.acesso = acesso;
  }
}
