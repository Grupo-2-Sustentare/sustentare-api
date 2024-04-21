package com.example.sustentaree.domain.item;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import jakarta.persistence.*;


@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeNota;
    private Boolean perecivel;
    private Integer diaVenciemento;

    @ManyToOne
    @JoinColumn(name = "fkUnidadeMedida")
    private UnidadeMedida unidadeMedida;

    @ManyToOne
    @JoinColumn(name = "fkCategoria")
    private CategoriaItem categoriaItem;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
