package com.example.sustentaree.domain.produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
