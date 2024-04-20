package com.example.sustentaree.dtos.item;

import com.example.sustentaree.domain.categoria.Categoria;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



public class ItemDTO {

    private String nomeNota;
    private Boolean perecivel;
    private Integer diaVenciemento;
    private UnidadeMedida unidadeMedida;
    private Categoria categoria;






    public String getNomeNota() {
        return nomeNota;
    }

    public void setNomeNota(String nomeNota) {
        this.nomeNota = nomeNota;
    }

    public Boolean getPerecivel() {
        return perecivel;
    }

    public void setPerecivel(Boolean perecivel) {
        this.perecivel = perecivel;
    }

    public Integer getDiaVenciemento() {
        return diaVenciemento;
    }

    public void setDiaVenciemento(Integer diaVenciemento) {
        this.diaVenciemento = diaVenciemento;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
