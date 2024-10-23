package com.example.sustentaree.dtos;

import java.math.BigDecimal;

public class KpiValorTotalSaidasDTO {
  private BigDecimal totalSaidas;

  public KpiValorTotalSaidasDTO(BigDecimal totalSaidas) {
    this.totalSaidas = totalSaidas;
  }

  // Getter e Setter
  public BigDecimal getTotalSaidas() {
    return totalSaidas;
  }

  public void setTotalSaidas(BigDecimal totalSaidas) {
    this.totalSaidas = totalSaidas;
  }
}