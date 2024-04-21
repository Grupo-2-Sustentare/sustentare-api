package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.item.AlterarItemDTO;
import com.example.sustentaree.dtos.item.ItemDTO;
import com.example.sustentaree.mapper.ItemMapper;
import com.example.sustentaree.repositories.CategoriaRepository;
import com.example.sustentaree.repositories.ItemRepository;
import com.example.sustentaree.repositories.UnidadeMedidaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itens")
public class ItemController {
    private ItemRepository itemRepository;
    private UnidadeMedidaRepository unidadeMedidaRepository;
    private CategoriaRepository categoriaRepository;

    public ItemController(ItemRepository itemRepository, UnidadeMedidaRepository unidadeMedidaRepository, CategoriaRepository categoriaRepository) {
        this.itemRepository = itemRepository;
        this.unidadeMedidaRepository = unidadeMedidaRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping("/{id1}/{id2}")
    public ResponseEntity<ItemDTO> criar(@PathVariable Integer id1, @PathVariable Integer id2,@RequestBody ItemDTO itemDTO) {
        Optional<UnidadeMedida> unidadeMedidaOptional = unidadeMedidaRepository.findById(id1);
        Optional<CategoriaItem> categoriaItemOptional = categoriaRepository.findById(id2);
        if (unidadeMedidaOptional.isPresent() && categoriaItemOptional.isPresent()) {
            itemDTO.setUnidade_medida(unidadeMedidaOptional.get());
            itemDTO.setCategoria(categoriaItemOptional.get());
            Item toItem = ItemMapper.INSTANCE.toItem(itemDTO);
            Item item = itemRepository.save(toItem);
            return ResponseEntity.ok(ItemMapper.INSTANCE.toItemDTO(item));
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<ItemDTO>> listar() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.INSTANCE.toItemList(items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            return ResponseEntity.ok(ItemMapper.INSTANCE.toItemDTO(itemOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemDTO> atualizar(@PathVariable Integer id, @RequestBody AlterarItemDTO alterarItemDTO) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        Optional<UnidadeMedida> unidadeMedidaOptional = unidadeMedidaRepository.findById(alterarItemDTO.getId_unidade_medida());
        Optional<CategoriaItem> categoriaItemOptional = categoriaRepository.findById(alterarItemDTO.getId_categoria());
        if (itemOptional.isPresent() && unidadeMedidaOptional.isPresent() && categoriaItemOptional.isPresent()) {
            alterarItemDTO.setUnidade_medida(unidadeMedidaOptional.get());
            alterarItemDTO.setCategoria(categoriaItemOptional.get());
            Item toItem = ItemMapper.INSTANCE.toItem(alterarItemDTO);
            toItem.setId(itemOptional.get().getId());
            Item itemSalvo = itemRepository.save(toItem);
            return ResponseEntity.ok(ItemMapper.INSTANCE.toItemDTO(itemSalvo));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            itemRepository.delete(itemOptional.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


}
