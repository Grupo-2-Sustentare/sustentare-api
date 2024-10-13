package com.example.sustentaree.controllers.autenticacao.dto;

public class UsuarioTokenDto {
  private Integer id;
  private String nome;
  private String senha;
  private String token;
  private Boolean ativo;
  private Integer acesso;

  public Integer getAcesso() {
    return acesso;
  }

  public void setAcesso(Integer acesso) {
    this.acesso = acesso;
  }

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

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
