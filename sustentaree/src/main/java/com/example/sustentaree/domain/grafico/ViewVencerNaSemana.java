package com.example.sustentaree.domain.grafico;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vw_vencer_na_semana")
public class ViewVencerNaSemana {
    @Id
    private String nome;
    private Integer vencidos;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getVencidos() {
        return vencidos;
    }

    public void setVencidos(Integer vencidos) {
        this.vencidos = vencidos;
    }
}
