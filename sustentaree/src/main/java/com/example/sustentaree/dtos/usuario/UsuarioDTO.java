package com.example.sustentaree.dtos.usuario;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UsuarioDTO {
    private String nome;
    private String senha;
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

    public Integer getAcesso() {
        return acesso;
    }

    public void setAcesso(Integer acesso) {
        this.acesso = acesso;
    }
}
