package com.example.sustentaree.dtos.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

public class AlterarProdutoDTO {

    // @ManyToOne -> trazer o id do Item correspondente ao produto
    private Integer fkItem;

    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private Double preco;
    @NotNull
    @Positive
    private Integer qtdProduto;
    @NotNull
    @Positive
    private Double qtdMedida;

    public Integer getFkItem() {
        return fkItem;
    }

    public void setFkItem(Integer fkItem) {
        this.fkItem = fkItem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(Integer qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public Double getQtdMedida() {
        return qtdMedida;
    }

    public void setQtdMedida(Double qtdMedida) {
        this.qtdMedida = qtdMedida;
    }
}
