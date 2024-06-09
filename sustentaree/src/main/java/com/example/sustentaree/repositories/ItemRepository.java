package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "SELECT * FROM item_parado;",nativeQuery = true)
    Item findByItemParado();

    @Query(value = "select * from item_vencimento;",nativeQuery = true)
    List<Item> getItemVencimento();
}
