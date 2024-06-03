package com.example.sustentaree.dtos.produto;

import com.example.sustentaree.domain.item.Item;
import lombok.Data;

public class ProdutoListagemDTO {

  private Integer id;

  // @ManyToOne -> trazer o id do Item correspondente ao produto

  private String nome;
  private Double preco;
  private Integer qtdProduto;
  private Double qtdMedida;

  private Item item;

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
}
