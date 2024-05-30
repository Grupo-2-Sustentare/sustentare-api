package com.example.sustentaree.dtos.fechamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

public class FechamentoCriacaoDTO {

    @NotNull
    @Future
    private LocalDateTime dataFim;
    @NotNull
    @PastOrPresent
    private LocalDateTime dataInicio;

    @NotNull
    private LocalDateTime dataFechamento;
    @NotNull
    private int isManual;

//    @OneToMany
//    private InteracaoEstoque interacaoEstoque;


    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public int getIsManual() {
        return isManual;
    }

    public void setIsManual(int isManual) {
        this.isManual = isManual;
    }
}
