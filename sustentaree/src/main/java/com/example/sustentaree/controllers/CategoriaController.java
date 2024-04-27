package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.dtos.categoria.CategoriaItemDTO;
import com.example.sustentaree.mapper.ItemCategoriaMapper;
import com.example.sustentaree.repositories.CategoriaRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @PostMapping
    public ResponseEntity<CategoriaItemDTO> criar(@RequestBody @Valid CategoriaItemDTO categoriaItemDTO) {
        System.out.println(categoriaItemDTO.getNome());
        CategoriaItem categoriaItem = ItemCategoriaMapper.INSTANCE.toCategoriaItem(categoriaItemDTO);
        categoriaRepository.save(categoriaItem);
        return ResponseEntity.ok(ItemCategoriaMapper.INSTANCE.toCategoriaItemDTO(categoriaItem));
    }

    @GetMapping
    public ResponseEntity<List<CategoriaItemDTO>> findAll() {
        List<CategoriaItem> categoriaItems = categoriaRepository.findAll();
        if (categoriaItems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CategoriaItemDTO> categoriasDTO = ItemCategoriaMapper.INSTANCE.toCategoriaItemListDTO(categoriaItems);
        return ResponseEntity.ok(categoriasDTO);

   }

   @GetMapping("/{id}")
    public ResponseEntity<CategoriaItemDTO> buscarPorID(@PathVariable Integer id) {
        Optional<CategoriaItem> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(ItemCategoriaMapper.INSTANCE.toCategoriaItemDTO(categoria.get()));
        }
        return ResponseEntity.notFound().build();
   }

   @PutMapping("/{id}")
    public ResponseEntity<CategoriaItemDTO> atualizar(@PathVariable Integer id, @RequestBody CategoriaItemDTO categoriaItemDTO) {
        Optional<CategoriaItem> optionalCategoria = categoriaRepository.findById(id);
        if (optionalCategoria.isPresent()) {
            CategoriaItem toCategoriaItem = ItemCategoriaMapper.INSTANCE.toCategoriaItem(categoriaItemDTO);
            toCategoriaItem.setId(optionalCategoria.get().getId());
            CategoriaItem categoriaItem = categoriaRepository.save(toCategoriaItem);
            return ResponseEntity.ok(ItemCategoriaMapper.INSTANCE.toCategoriaItemDTO(categoriaItem));
        }
        return ResponseEntity.notFound().build();
   }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        Optional<CategoriaItem> optionalCategoria = categoriaRepository.findById(id);
        if (optionalCategoria.isPresent()) {
            CategoriaItem toCategoriaItem = optionalCategoria.get();
            categoriaRepository.delete(toCategoriaItem);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
   }



}
