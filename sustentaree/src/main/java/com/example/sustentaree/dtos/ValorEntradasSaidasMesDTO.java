package com.example.sustentaree.dtos;

public class ValorEntradasSaidasMesDTO {
  private String mesAno;
  private String categoria;
  private String item;
  private Double valorEntradas;
  private Double valorSaidas;

  public ValorEntradasSaidasMesDTO(String mesAno, String categoria, String item, Double valorEntradas, Double valorSaidas) {
    this.mesAno = mesAno;
    this.categoria = categoria;
    this.item = item;
    this.valorEntradas = valorEntradas;
    this.valorSaidas = valorSaidas;
  }

  // Getters e Setters
  public String getMesAno() {
    return mesAno;
  }

  public void setMesAno(String mesAno) {
    this.mesAno = mesAno;
  }

  public String getCategoria() {
    return categoria;
  }

  public void setCategoria(String categoria) {
    this.categoria = categoria;
  }

  public String getItem() {
    return item;
  }

  public void setItem(String item) {
    this.item = item;
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