package com.example.sustentaree.domain.grafico;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name = "item_parado")
public class ViewItemParado {
    @Id
    private String nome;
    private Integer dias_vencimento;
    private Integer dias_no_estoque;
    private Integer vencido;
    private Double porcentagem_do_prazo;
    private Integer dias_pos_vencimento;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDias_vencimento() {
        return dias_vencimento;
    }

    public void setDias_vencimento(Integer dias_vencimento) {
        this.dias_vencimento = dias_vencimento;
    }

    public Integer getDias_no_estoque() {
        return dias_no_estoque;
    }

    public void setDias_no_estoque(Integer dias_no_estoque) {
        this.dias_no_estoque = dias_no_estoque;
    }

    public Integer getVencido() {
        return vencido;
    }

    public void setVencido(Integer vencido) {
        this.vencido = vencido;
    }

    public Double getPorcentagem_do_prazo() {
        return porcentagem_do_prazo;
    }

    public void setPorcentagem_do_prazo(Double porcentagem_do_prazo) {
        this.porcentagem_do_prazo = porcentagem_do_prazo;
    }

    public Integer getDias_pos_vencimento() {
        return dias_pos_vencimento;
    }

    public void setDias_pos_vencimento(Integer dias_pos_vencimento) {
        this.dias_pos_vencimento = dias_pos_vencimento;
    }
}
