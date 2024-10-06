package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, Integer> {
  List<UnidadeMedida> findByAtivoTrue();

  @Modifying
  @Transactional
  @Query("UPDATE UnidadeMedida u SET u.ativo = :ativo WHERE u.id = :id")
  void updateAtivoById(@Param("ativo") Boolean ativo, @Param("id") Integer id);

    @Query("SELECT u FROM UnidadeMedida u WHERE u.nome = :nome")
    UnidadeMedida findByNome(@Param("nome") String nome);
}
