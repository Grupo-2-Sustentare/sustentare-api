package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.produto.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
  List<Produto> findByAtivoTrue();

  @Modifying
  @Transactional
  @Query("UPDATE Produto u SET u.ativo = :ativo WHERE u.id = :id")
  void updateAtivoById(@Param("ativo") Boolean ativo, @Param("id") Integer id);
}
