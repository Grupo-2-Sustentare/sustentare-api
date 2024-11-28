package com.example.sustentaree.dtos.produto;

import com.example.sustentaree.domain.item.Item;
import lombok.Data;

public class ProdutoListagemDTO {

  private Integer id;

  // @ManyToOne -> trazer o id do Item correspondente ao produto

  private Double preco;
  private Double qtdProduto;
  private Double qtdProdutoTotal;
  private Double qtdMedida;
  private Item item;

  public Double getQtdProduto() {
    return qtdProduto;
  }

  public void setQtdProduto(Double qtdProduto) {
    this.qtdProduto = qtdProduto;
  }

  public Double getQtdProdutoTotal() {
    return qtdProdutoTotal;
  }

  public void setQtdProdutoTotal(Double qtdProdutoTotal) {
    this.qtdProdutoTotal = qtdProdutoTotal;
  }

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

  public Double getPreco() {
    return preco;
  }

  public void setPreco(Double preco) {
    this.preco = preco;
  }

  public Double getQtdMedida() {
    return qtdMedida;
  }

  public void setQtdMedida(Double qtdMedida) {
    this.qtdMedida = qtdMedida;
  }
}
