package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemRepository extends JpaRepository<Item, Integer> {
}
