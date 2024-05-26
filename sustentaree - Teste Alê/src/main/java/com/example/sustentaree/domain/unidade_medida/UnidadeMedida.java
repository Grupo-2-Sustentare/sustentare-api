package com.example.sustentaree.domain.unidade_medida;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class UnidadeMedida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String categoria;
    private Double conversaoPadrao;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
