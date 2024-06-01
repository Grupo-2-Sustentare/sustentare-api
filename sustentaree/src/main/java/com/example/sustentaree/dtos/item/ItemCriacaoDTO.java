package com.example.sustentaree.dtos.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public class ItemCriacaoDTO {
    @NotBlank
    private String nome;
    @NotNull
    private Boolean perecivel;
    private Integer diasVencimento;

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

    public Integer getDiasVencimento() {
        return diasVencimento;
    }

    public void setDiasVencimento(Integer diasVencimento) {
        this.diasVencimento = diasVencimento;
    }
}
