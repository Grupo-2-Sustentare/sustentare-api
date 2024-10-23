package com.example.sustentaree.dtos;

public class ComprasDTO {
  private String mesAno;
  private String tipoCompra;
  private Integer qtdCompras;

  public ComprasDTO(String mesAno, String tipoCompra, Integer qtdCompras) {
    this.mesAno = mesAno;
    this.tipoCompra = tipoCompra;
    this.qtdCompras = qtdCompras;
  }

  // Getters e Setters
  public String getMesAno() {
    return mesAno;
  }

  public void setMesAno(String mesAno) {
    this.mesAno = mesAno;
  }

  public String getTipoCompra() {
    return tipoCompra;
  }

  public void setTipoCompra(String tipoCompra) {
    this.tipoCompra = tipoCompra;
  }

  public Integer getQtdCompras() {
    return qtdCompras;
  }

  public void setQtdCompras(Integer qtdCompras) {
    this.qtdCompras = qtdCompras;
  }
}