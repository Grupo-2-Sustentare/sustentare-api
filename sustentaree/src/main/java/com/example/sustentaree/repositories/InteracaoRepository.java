package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface InteracaoRepository extends JpaRepository<InteracaoEstoque, Integer> {
    @Query(value = "SELECT * FROM ultima_adicao;",nativeQuery = true)
    InteracaoEstoque findByUltimaAdicao();
}
