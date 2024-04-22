package com.example.sustentaree.dtos.unidade_medida;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class UnidadeMedidaDTO {
    @Schema(description = "Nome da unidade de medida", example = "Quilograma")
    private String categoria;
    @Schema(description = "Conversão padrão da unidade de medida", example = "1.0")
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
