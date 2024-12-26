package com.example.sustentaree.domain.audit;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "item_audit")
public class ItemAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idItemAudit;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private Integer fkItem;

    @Column(nullable = false)
    private Integer fkUsuario;

    public ItemAudit() {
    }

    public ItemAudit(String descricao, LocalDateTime dataHora, Integer fkItem, Integer fkUsuario) {
        this.descricao = descricao;
        this.dataHora = dataHora;
        this.fkItem = fkItem;
        this.fkUsuario = fkUsuario;
    }

    public Integer getIdItemAudit() {
        return idItemAudit;
    }

    public void setIdItemAudit(Integer idItemAudit) {
        this.idItemAudit = idItemAudit;
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

    public Integer getFkItem() {
        return fkItem;
    }

    public void setFkItem(Integer fkItem) {
        this.fkItem = fkItem;
    }

    public Integer getFkUsuario() {
        return fkUsuario;
    }

    public void setFkUsuario(Integer fkUsuario) {
        this.fkUsuario = fkUsuario;
    }
}
