package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.services.InteracaoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InteracaoRepository extends JpaRepository<InteracaoEstoque, Integer> {
    @Query(value = "SELECT * FROM ultima_adicao;",nativeQuery = true)
    InteracaoEstoque findByUltimaAdicao();

    @Query(value = "SELECT * FROM interacao_estoque JOIN produto ON interacao_estoque.fk_produto = produto.id_produto;",nativeQuery = true)
    List<InteracaoEstoque> getMaiorRetirada();
}
