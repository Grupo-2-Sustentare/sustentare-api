package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.usuario.Usuario;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoriaItemRepository extends JpaRepository<CategoriaItem, Integer> {
  List<CategoriaItem> findByAtivoTrueOrderByNome();
  @Modifying
  @Transactional
  @Query("UPDATE CategoriaItem u SET u.ativo = :ativo WHERE u.id = :id")
  void updateAtivoById(@Param("ativo") Boolean ativo, @Param("id") Integer id);

    @Query("SELECT u FROM CategoriaItem u WHERE u.nome = :nome")
    CategoriaItem findByNome(@Param("nome") String nome);
}
