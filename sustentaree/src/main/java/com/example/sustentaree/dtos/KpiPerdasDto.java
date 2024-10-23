package com.example.sustentaree.dtos;

public class KpiPerdasDto {
  private int totalPerdas;
  private String situacao;

  public KpiPerdasDto(int totalPerdas, String situacao) {
    this.totalPerdas = totalPerdas;
    this.situacao = situacao;
  }

  // Getters and Setters
  public int getTotalPerdas() {
    return totalPerdas;
  }

  public void setTotalPerdas(int totalPerdas) {
    this.totalPerdas = totalPerdas;
  }

  public String getSituacao() {
    return situacao;
  }

  public void setSituacao(String situacao) {
    this.situacao = situacao;
  }
}