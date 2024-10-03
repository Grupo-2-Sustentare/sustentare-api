package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedida, Integer> {
  List<UnidadeMedida> findByAtivoTrue();
}
