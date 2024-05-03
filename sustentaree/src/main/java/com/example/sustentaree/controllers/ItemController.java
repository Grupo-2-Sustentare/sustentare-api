package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.item.AlterarItemDTO;
import com.example.sustentaree.dtos.item.ItemCriacaoDTO;
import com.example.sustentaree.dtos.item.ItemListagemDTO;
import com.example.sustentaree.mapper.ItemMapper;
import com.example.sustentaree.repositories.CategoriaRepository;
import com.example.sustentaree.repositories.ItemRepository;
import com.example.sustentaree.repositories.UnidadeMedidaRepository;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<ItemListagemDTO> criar(@RequestBody @Valid ItemCriacaoDTO itemCriacaoDTO) {
        Optional<UnidadeMedida> unidadeMedidaOptional = unidadeMedidaRepository.findById(itemCriacaoDTO.getId_unidade_medida());
        Optional<CategoriaItem> categoriaItemOptional = categoriaRepository.findById(itemCriacaoDTO.getId_categoria());
        if (unidadeMedidaOptional.isPresent() && categoriaItemOptional.isPresent()) {
            Item toItem = ItemMapper.INSTANCE.toItem(itemCriacaoDTO,categoriaItemOptional.get(),unidadeMedidaOptional.get());
            System.out.println(toItem.getUnidade_medida());
            Item item = itemRepository.save(toItem);
            ItemListagemDTO toItemListagemDTO = ItemMapper.INSTANCE.toItemListagemDTO(item);
            return ResponseEntity.ok(toItemListagemDTO);
        }
        return ResponseEntity.badRequest().build();
    }

    @GetMapping
    public ResponseEntity<List<ItemListagemDTO>> listar() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.INSTANCE.toItemList(items));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemListagemDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            return ResponseEntity.ok(ItemMapper.INSTANCE.toItemListagemDTO(itemOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<ItemListagemDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AlterarItemDTO alterarItemDTO) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        Optional<UnidadeMedida> unidadeMedidaOptional = unidadeMedidaRepository.findById(alterarItemDTO.getId_unidade_medida());
        Optional<CategoriaItem> categoriaItemOptional = categoriaRepository.findById(alterarItemDTO.getId_categoria());
        if (itemOptional.isPresent() && unidadeMedidaOptional.isPresent() && categoriaItemOptional.isPresent()) {
            alterarItemDTO.setUnidade_medida(unidadeMedidaOptional.get());
            alterarItemDTO.setCategoria(categoriaItemOptional.get());
            Item toItem = ItemMapper.INSTANCE.toItem(alterarItemDTO);
            toItem.setId(itemOptional.get().getId());
            Item itemSalvo = itemRepository.save(toItem);
            return ResponseEntity.ok(ItemMapper.INSTANCE.toItemListagemDTO(itemSalvo));
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
