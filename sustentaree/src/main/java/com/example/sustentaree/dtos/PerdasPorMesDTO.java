package com.example.sustentaree.dtos;

public class PerdasPorMesDTO {
  private String mesAno;
  private String tipoPerda;
  private Integer qtdPerda;

  public PerdasPorMesDTO(String mesAno, String tipoPerda, Integer qtdPerda) {
    this.mesAno = mesAno;
    this.tipoPerda = tipoPerda;
    this.qtdPerda = qtdPerda;
  }

  public String getMesAno() {
    return mesAno;
  }

  public void setMesAno(String mesAno) {
    this.mesAno = mesAno;
  }

  public String getTipoPerda() {
    return tipoPerda;
  }

  public void setTipoPerda(String tipoPerda) {
    this.tipoPerda = tipoPerda;
  }

  public Integer getQtdPerda() {
    return qtdPerda;
  }

  public void setQtdPerda(Integer qtdPerda) {
    this.qtdPerda = qtdPerda;
  }
}