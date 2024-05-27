package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaItemRepository extends JpaRepository<CategoriaItem, Integer> {
}
