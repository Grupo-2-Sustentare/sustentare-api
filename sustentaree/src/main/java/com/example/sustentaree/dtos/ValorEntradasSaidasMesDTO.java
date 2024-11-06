package com.example.sustentaree.dtos;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValorEntradasSaidasMesDTO {
  private String diaMes;
  private Double valorEntradas;
  private Double valorSaidas;
  private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM");

  public ValorEntradasSaidasMesDTO(LocalDate data, Double valorEntradas, Double valorSaidas) {
    this.diaMes = data.format(FORMATTER);
    this.valorEntradas = valorEntradas;
    this.valorSaidas = valorSaidas;
  }

  public String getDiaMes() {
    return diaMes;
  }

  public void setDiaMes(String diaMes) {
    this.diaMes = diaMes;
  }

  public Double getValorEntradas() {
    return valorEntradas;
  }

  public void setValorEntradas(Double valorEntradas) {
    this.valorEntradas = valorEntradas;
  }

  public Double getValorSaidas() {
    return valorSaidas;
  }

  public void setValorSaidas(Double valorSaidas) {
    this.valorSaidas = valorSaidas;
  }
}