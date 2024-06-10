package com.example.sustentaree.domain.grafico;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_interacoes_por_mes")
public class ViewEntradasSaidas {
    @Id
    private String mes;
    private Integer entradas;
    private Integer saidas;

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getEntradas() {
        return entradas;
    }

    public void setEntradas(Integer entradas) {
        this.entradas = entradas;
    }

    public Integer getSaidas() {
        return saidas;
    }

    public void setSaidas(Integer saidas) {
        this.saidas = saidas;
    }
}
