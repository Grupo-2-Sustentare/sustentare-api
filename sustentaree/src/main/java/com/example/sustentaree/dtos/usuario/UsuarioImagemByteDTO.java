package com.example.sustentaree.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UsuarioImagemByteDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String senha;
    @NotBlank
    private String email;
    @NotNull
    private Integer acesso;
    private Boolean ativo;

    private byte[] imagem;

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

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
}
