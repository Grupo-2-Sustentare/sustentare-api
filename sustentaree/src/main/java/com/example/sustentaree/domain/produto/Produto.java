package com.example.sustentaree.domain.produto;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Produto {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_produto")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "fk_item")
  private Item item;
  private String nome;
  private Double preco;
  @Column(name = "qtd_produto")
  private Integer qtdProduto;
  @Column(name = "qtd_medida")
  private Double qtdMedida;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Item getItem() {
    return item;
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public Integer getQtdProduto() {
    return qtdProduto;
  }

  public void setQtdProduto(Integer qtdProduto) {
    this.qtdProduto = qtdProduto;
  }

  public Double getQtdMedida() {
    return qtdMedida;
  }

  public void setQtdMedida(Double qtdMedida) {
    this.qtdMedida = qtdMedida;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("Produto{");
    sb.append("id=").append(id);
    sb.append(", item=").append(item);
    sb.append(", nome='").append(nome).append('\'');
    sb.append(", preco=").append(preco);
    sb.append(", qtdProduto=").append(qtdProduto);
    sb.append(", qtdMedida=").append(qtdMedida);
    sb.append('}');
    return sb.toString();
  }
}
