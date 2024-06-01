package com.example.sustentaree.dtos.produto;

import lombok.Data;

public class ProdutoListagemDTO {

    private Integer id;

    // @ManyToOne -> trazer o id do Item correspondente ao produto
    private Integer fkItem;

    private String nome;
    private Double preco;
    private Integer qtdProduto;
    private Double qtdMedida;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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