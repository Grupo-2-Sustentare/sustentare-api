package com.example.sustentaree.dtos.item;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;


public class ItemDTO {

    private String nomeNota;
    private Boolean perecivel;
    private Integer diaVenciemento;
    private UnidadeMedida unidadeMedida;
    private CategoriaItem categoriaItem;






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

    public CategoriaItem getCategoria() {
        return categoriaItem;
    }

    public void setCategoria(CategoriaItem categoriaItem) {
        this.categoriaItem = categoriaItem;
    }
}
