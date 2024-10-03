package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriaItemRepository extends JpaRepository<CategoriaItem, Integer> {
  List<CategoriaItem> findByAtivoTrue();
}
