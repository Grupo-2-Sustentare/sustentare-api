package com.example.sustentaree.dtos.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class AlterarUsuarioDTO {
    @Schema(description = "Senha do usuário", example = "123456")
    private String senha;
    @Schema(description = "Nível de acesso do usuário", example = "1")
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
