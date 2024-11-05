package com.example.sustentaree.dtos;

public class PerdasPorMesDTO {
  private String tipoPerda;
  private Integer qtdPerda;

  public PerdasPorMesDTO(String tipoPerda, Integer qtdPerda) {
    this.tipoPerda = tipoPerda;
    this.qtdPerda = qtdPerda;
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