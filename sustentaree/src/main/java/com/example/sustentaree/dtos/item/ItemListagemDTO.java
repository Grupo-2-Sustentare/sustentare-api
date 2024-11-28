package com.example.sustentaree.dtos.item;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


public class ItemListagemDTO {

  private Integer id;
  private String nome;
  private Boolean perecivel;
  private Integer dias_vencimento;
  private Double qtd_min_item;
  private String imagem;
  private Boolean ativo;

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  public String getImagem() {
    return imagem;
  }

  public void setImagem(String imagem) {
    this.imagem = imagem;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Boolean getPerecivel() {
    return perecivel;
  }

  public void setPerecivel(Boolean perecivel) {
    this.perecivel = perecivel;
  }

  public Integer getDias_vencimento() {
    return dias_vencimento;
  }

  public void setDias_vencimento(Integer dias_vencimento) {
    this.dias_vencimento = dias_vencimento;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Double getQtd_min_item() {
    return qtd_min_item;
  }

  public void setQtd_min_item(Double qtd_min_item) {
    this.qtd_min_item = qtd_min_item;
  }
}
