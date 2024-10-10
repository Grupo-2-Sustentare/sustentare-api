package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "SELECT * FROM item_parado;",nativeQuery = true)
    Item findByItemParado();

    @Query(value = "select * from item_vencimento;",nativeQuery = true)
    List<Item> getItemVencimento();

    List<Item> findByAtivoTrue();

    @Modifying
    @Transactional
    @Query("UPDATE Item u SET u.ativo = :ativo WHERE u.id = :id")
    void updateAtivoById(@Param("ativo") Boolean ativo, @Param("id") Integer id);

    @Query(value = "SELECT * FROM item WHERE nome = :nome",nativeQuery = true)
    Optional<Item> existsByNome(String nome);
}
