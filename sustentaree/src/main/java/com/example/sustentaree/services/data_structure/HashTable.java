package com.example.sustentaree.services.data_structure;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.produto.Produto;

import java.util.List;
import java.util.concurrent.CancellationException;

public class HashTable {
  private final ListaLigada<Produto>[] tab;
  private List<CategoriaItem> categorias;

  public HashTable(List<CategoriaItem> categorias){
  this.tab = new ListaLigada[categorias.size()];
    for (int i = 0; i < categorias.size(); i++){
      tab[i] = new ListaLigada<>();
    }
    this.categorias = categorias;
  }

  public void hashFunction(Produto prod) {
    for (int i =0; i < this.categorias.size(); i++) {
      if (prod.getItem().getCategoria().getNome().equals(categorias.get(i).getNome())) {
        tab[i].insert(prod);
      }
    }
  }

  public void insertMany(List<Produto> prods){
      for (Produto prod: prods){
        hashFunction(prod);
    }
  }

  public List<Produto> findProdutoByCategoria(String value){
    for (int i =0; i < this.categorias.size(); i++) {
      if(value.equals(categorias.get(i).getNome())){
        return tab[i].getAllNodeValues();
      }
    }
    return null;
  }

}