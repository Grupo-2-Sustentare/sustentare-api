package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.fechamento.Fechamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FechamentoRepository extends JpaRepository<Fechamento, Integer> {
}
