package com.example.sustentaree.domain.interacao_estoque;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class InteracaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_interacao_estoque")
    private Integer id;

//    @ManyToOne -> trazer o id do Produto correspondente até interação
    @Column(name = "fk_produto")
    private Integer fkProduto;
    @Column(name = "data_hora")
    private LocalDateTime dataHora;

//    0 ou 1 fechamento estoque para cada interação do estoque
    @Column(name = "fk_fechamento_estoque")
    private Integer fkFechamentoEstoque;

    @Column(name = "categoria_interacao")
    private String categoriaInteracao;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(Integer fkProduto) {
        this.fkProduto = fkProduto;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getFkFechamentoEstoque() {
        return fkFechamentoEstoque;
    }

    public void setFkFechamentoEstoque(Integer fkFechamentoEstoque) {
        this.fkFechamentoEstoque = fkFechamentoEstoque;
    }

    public String getCategoriaInteracao() {
        return categoriaInteracao;
    }

    public void setCategoriaInteracao(String categoriaInteracao) {
        this.categoriaInteracao = categoriaInteracao;
    }
}
