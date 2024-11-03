package com.example.sustentaree.dtos;

public class EntradasSaidasColaboradoresDTO {
  private Integer colaborador;
  private Integer qtdEntradas;
  private Integer qtdSaidas;

  public EntradasSaidasColaboradoresDTO(Integer colaborador, Integer qtdEntradas, Integer qtdSaidas) {
    this.colaborador = colaborador;
    this.qtdEntradas = qtdEntradas;
    this.qtdSaidas = qtdSaidas;
  }

  public Integer getColaborador() {
    return colaborador;
  }

  public void setColaborador(Integer colaborador) {
    this.colaborador = colaborador;
  }

  public Integer getQtdEntradas() {
    return qtdEntradas;
  }

  public void setQtdEntradas(Integer qtdEntradas) {
    this.qtdEntradas = qtdEntradas;
  }

  public Integer getQtdSaidas() {
    return qtdSaidas;
  }

  public void setQtdSaidas(Integer qtdSaidas) {
    this.qtdSaidas = qtdSaidas;
  }
}
