package com.example.sustentaree.controllers.kpis;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/kpis")
public class Kpi {
    private final ItemService itemService;

    public Kpi(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ResponseEntity<Item> getKpi() {
        Item item = itemService.itemParado();
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(item);
    }

}
