package com.example.sustentaree.dtos.item;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;

public class AlterarItemDTO {
    @Schema(description = "Nome do item", example = "Feijão")
    private String nome;
    @Schema(description = "Se o item é perecível", example = "false")
    private Boolean perecivel;
    @Schema(description = "Quantos dias até o vencimento do item", example = "0")
    private Integer dia_vencimento;
    @Schema(description = "Unidade de medida do item", example = "Kg")
    private UnidadeMedida unidade_medida;
    @Schema(description = "Categoria do item", example = "Alimentos")
    private CategoriaItem categoria;
    @Schema(description = "Id da unidade de medida do item", example = "1")
    private Integer id_unidade_medida;
    @Schema(description = "Id da categoria do item", example = "1")
    private Integer id_categoria;


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

    public Integer getId_unidade_medida() {
        return id_unidade_medida;
    }

    public void setId_unidade_medida(Integer id_unidade_medida) {
        this.id_unidade_medida = id_unidade_medida;
    }

    public Integer getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Integer id_categoria) {
        this.id_categoria = id_categoria;
    }
}
