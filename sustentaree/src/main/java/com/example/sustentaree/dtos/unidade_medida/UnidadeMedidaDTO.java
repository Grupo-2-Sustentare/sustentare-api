package com.example.sustentaree.dtos.unidade_medida;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UnidadeMedidaDTO {
    private String categoria;
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
