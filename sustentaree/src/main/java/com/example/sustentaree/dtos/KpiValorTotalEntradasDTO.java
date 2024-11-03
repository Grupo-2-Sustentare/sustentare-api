package com.example.sustentaree.dtos;

import java.math.BigDecimal;

public class KpiValorTotalEntradasDTO {
  private BigDecimal totalEntradas;

  public KpiValorTotalEntradasDTO(BigDecimal totalEntradas) {
    this.totalEntradas = totalEntradas;
  }

  public BigDecimal getTotalEntradas() {
    return totalEntradas;
  }

  public void setTotalEntradas(BigDecimal totalEntradas) {
    this.totalEntradas = totalEntradas;
  }
}
