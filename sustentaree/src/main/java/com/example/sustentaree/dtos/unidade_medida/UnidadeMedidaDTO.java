package com.example.sustentaree.dtos.unidade_medida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UnidadeMedidaDTO {

    @NotBlank
    private String categoria;
    @NotNull
    private Double conversaoPadrao;

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getConversaoPadrao() {
        return conversaoPadrao;
    }

    public void setConversaoPadrao(Double conversaoPadrao) {
        this.conversaoPadrao = conversaoPadrao;
    }
}
