package com.example.sustentaree.dtos.usuario;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class AlterarUsuarioDTO {
    private String senha;
    private Integer acesso;


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
