package com.example.sustentaree.dtos;

public class ComprasDTO {
  private String tipoCompra;
  private Integer qtdCompras;

  public ComprasDTO(String tipoCompra, Integer qtdCompras) {
    this.tipoCompra = tipoCompra;
    this.qtdCompras = qtdCompras;
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