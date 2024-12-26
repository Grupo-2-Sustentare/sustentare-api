package com.example.sustentaree.domain.audit;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "interacao_estoque_audit")
public class InteracaoEstoqueAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_audit")
    private Integer idInteracaoEstoqueAudit;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private Integer fkUsuario;

    @Column(nullable = false)
    private Integer fkInteracaoEstoque;

    @Column(nullable = false)
    private Integer fkProduto;

    public InteracaoEstoqueAudit(String descricao, LocalDateTime dataHora, Integer fkUsuario, Integer fkInteracaoEstoque, Integer fkProduto) {
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.fkUsuario = fkUsuario;
        this.fkInteracaoEstoque = fkInteracaoEstoque;
        this.fkProduto = fkProduto;
    }

    // Getters e Setters

    public Integer getIdInteracaoEstoqueAudit() {
        return idInteracaoEstoqueAudit;
    }

    public void setIdInteracaoEstoqueAudit(Integer idInteracaoEstoqueAudit) {
        this.idInteracaoEstoqueAudit = idInteracaoEstoqueAudit;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }

    public Integer getFkInteracaoEstoque() {
        return fkInteracaoEstoque;
    }

    public void setFkInteracaoEstoque(Integer fkInteracaoEstoque) {
        this.fkInteracaoEstoque = fkInteracaoEstoque;
    }

    public Integer getFkProduto() {
        return fkProduto;
    }

    public void setFkProduto(Integer fkProduto) {
        this.fkProduto = fkProduto;
    }
}