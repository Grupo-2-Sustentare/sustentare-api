package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
  List<Produto> findByAtivoTrue();
}
