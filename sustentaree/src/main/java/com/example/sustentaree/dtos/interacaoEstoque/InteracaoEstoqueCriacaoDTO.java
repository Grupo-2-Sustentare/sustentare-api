package com.example.sustentaree.dtos.interacaoEstoque;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

public class InteracaoEstoqueCriacaoDTO {


  @NotNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime dataHora;

  @NotBlank
  private String categoriaInteracao;

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }

  public String getCategoriaInteracao() {
    return categoriaInteracao;
  }

  public void setCategoriaInteracao(String categoriaInteracao) {
    this.categoriaInteracao = categoriaInteracao;
  }
}
