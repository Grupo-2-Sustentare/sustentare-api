package com.example.sustentaree.dtos.fechamento;

import lombok.Data;

import java.time.LocalDateTime;

public class FechamentoListagemDTO {

  private Integer id;

  private LocalDateTime dataFim;
  private LocalDateTime dataInicio;
  private LocalDateTime dataFechamento;
  private int isManual;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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
