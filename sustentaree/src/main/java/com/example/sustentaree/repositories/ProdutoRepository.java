package com.example.sustentaree.repositories;

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

  @Query("SELECT p FROM Produto p WHERE p.item.id = :itemId")
  List<Produto> findByItemId(@Param("itemId") Integer itemId);

  @Query(value = "SELECT p.* FROM produto p " +
        "JOIN item i ON p.fk_item = i.id_item " +
        "JOIN categoria_item ci ON i.fk_categoria_item = ci.id_categoria_item " +
        "WHERE FIND_IN_SET(ci.nome, :nomes)" +
          "ORDER BY i.nome",
        nativeQuery = true)
  List<Produto> listByCategory(@Param("nomes") String nomes);

}
