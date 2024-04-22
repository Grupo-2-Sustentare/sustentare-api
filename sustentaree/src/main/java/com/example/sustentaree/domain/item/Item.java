package com.example.sustentaree.domain.item;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;


@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Schema(description = "Nome do item", example = "Arroz")
    private String nome;
    @Schema(description = "Se o item é perecível", example = "true")
    private Boolean perecivel;
    @Schema(description = "Quantos dias até o vencimento do item", example = "30")
    private Integer dia_vencimento;


    @ManyToOne
    @JoinColumn(name = "fk_unidade_medida")
    private UnidadeMedida unidade_medida;

    @ManyToOne
    @JoinColumn(name = "fk_categoria_item")
    private CategoriaItem categoria;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getPerecivel() {
        return perecivel;
    }

    public void setPerecivel(Boolean perecivel) {
        this.perecivel = perecivel;
    }

    public Integer getDia_vencimento() {
        return dia_vencimento;
    }

    public void setDia_vencimento(Integer dia_vencimento) {
        this.dia_vencimento = dia_vencimento;
    }

    public UnidadeMedida getUnidade_medida() {
        return unidade_medida;
    }

    public void setUnidade_medida(UnidadeMedida unidade_medida) {
        this.unidade_medida = unidade_medida;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaItem categoria) {
        this.categoria = categoria;
    }
}
