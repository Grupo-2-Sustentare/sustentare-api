package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteracaoRepository extends JpaRepository<InteracaoEstoque, Integer> {

}
