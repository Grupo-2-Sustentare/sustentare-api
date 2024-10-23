package com.example.sustentaree.dtos;

public class KpiComprasNaoPlanejadasDTO {
  private Integer totalComprasNaoPlanejadas;
  private String situacao;

  public KpiComprasNaoPlanejadasDTO(Integer totalComprasNaoPlanejadas, String situacao) {
    this.totalComprasNaoPlanejadas = totalComprasNaoPlanejadas;
    this.situacao = situacao;
  }

  // Getters e Setters
  public Integer getTotalComprasNaoPlanejadas() {
    return totalComprasNaoPlanejadas;
  }

  public void setTotalComprasNaoPlanejadas(Integer totalComprasNaoPlanejadas) {
    this.totalComprasNaoPlanejadas = totalComprasNaoPlanejadas;
  }

  public String getSituacao() {
    return situacao;
  }

  public void setSituacao(String situacao) {
    this.situacao = situacao;
  }
}