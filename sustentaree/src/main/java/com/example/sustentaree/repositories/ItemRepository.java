package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ItemRepository extends JpaRepository<Item, Integer> {
    @Query(value = "SELECT * FROM item_parado;",nativeQuery = true)
    Item findByItemParado();
}
