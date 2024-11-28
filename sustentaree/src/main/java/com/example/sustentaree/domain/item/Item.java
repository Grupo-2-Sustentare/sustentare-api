package com.example.sustentaree.domain.item;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_item")
  private Integer id;
  @Schema(description = "Nome do item", example = "Arroz")
  private String nome;
  @Schema(description = "Se o item é perecível", example = "true")
  private Boolean perecivel;
  @Schema(description = "Quantos dias até o vencimento do item", example = "30")
  private Integer dias_vencimento;
  @Schema(description = "Quantidade minima do item para que o adm receba alerta", example = "2.0")
  private Double qtd_min_item;
  @ManyToOne
  @JoinColumn(name = "fk_unidade_medida")
  private UnidadeMedida unidade_medida;
  @ManyToOne
  @JoinColumn(name = "fk_categoria_item")
  private CategoriaItem categoria;
  private Boolean ativo;

  public Boolean getAtivo() {
    return ativo;
  }

  public void setAtivo(Boolean ativo) {
    this.ativo = ativo;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
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

  public UnidadeMedida getUnidade_medida() {
    return unidade_medida;
  }

  public void setUnidade_medida(UnidadeMedida unidade_medida) {
    this.unidade_medida = unidade_medida;
  }

  public CategoriaItem getCategoria() {
    return categoria;
  }

  public void setCategoria(CategoriaItem categoria) {
    this.categoria = categoria;
  }

  public Double getQtd_min_item() {
    return qtd_min_item;
  }

  public void setQtd_min_item(Double qtd_min_item) {
    this.qtd_min_item = qtd_min_item;
  }

  @Override
  public String toString() {
    return "Item{" +
            "id=" + id +
            ", nome='" + nome + '\'' +
            ", perecivel=" + perecivel +
            ", dias_vencimento=" + dias_vencimento +
            ", qtd_min_item=" + qtd_min_item +
            ", unidade_medida=" + unidade_medida +
            ", categoria=" + categoria +
            ", ativo=" + ativo +
            '}';
  }
}
