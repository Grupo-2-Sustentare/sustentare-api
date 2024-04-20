package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
