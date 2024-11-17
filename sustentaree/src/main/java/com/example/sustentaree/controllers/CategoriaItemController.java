package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.dtos.categoria.CategoriaItemDTO;
import com.example.sustentaree.services.CategoriaItemService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import com.example.sustentaree.mapper.CategoriaItemMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaItemController {
  @Autowired
  private final CategoriaItemService service;

  public CategoriaItemController(CategoriaItemService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<CategoriaItemDTO> criar(
      @Parameter(
          description = "Informações da categoria a ser criada",
          content = @Content(
              mediaType = "application/json",
              schema = @Schema(implementation = CategoriaItemDTO.class)
          ),
          examples = @ExampleObject(
              value = "{\n  \"nome\": \"Categoria Exemplo\",\n  \"descricao\": \"Descrição Exemplo\"\n}"
          )
      ) @RequestBody CategoriaItemDTO dto, @RequestParam int idResponsavel) {
    CategoriaItemMapper mapper = CategoriaItemMapper.INSTANCE;
    CategoriaItem entity = mapper.toCategoriaItem(dto);
    CategoriaItem novo = this.service.criar(entity, idResponsavel);

    CategoriaItemDTO response = mapper.toCategoriaItemDTO(novo);

    return ResponseEntity.ok(response);
  }

  @GetMapping
  public ResponseEntity<List<CategoriaItemDTO>> findByAtivoTrue() {
    List<CategoriaItem> categorias = this.service.listar();
    if (categorias.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    CategoriaItemMapper mapper = CategoriaItemMapper.INSTANCE;
    return ResponseEntity.ok(mapper.toCategoriaItemListDTO(categorias));
  }

  @GetMapping("/{id}")
  public ResponseEntity<CategoriaItemDTO> buscarPorID(@PathVariable Integer id) {
    CategoriaItem categoriaItem = this.service.porId(id);

    CategoriaItemMapper mapper = CategoriaItemMapper.INSTANCE;
    return ResponseEntity.ok(mapper.toCategoriaItemDTO(categoriaItem));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CategoriaItemDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid CategoriaItemDTO dto, @RequestParam int idResponsavel) {
    CategoriaItemMapper mapper = CategoriaItemMapper.INSTANCE;
    CategoriaItem entity = mapper.toCategoriaItem(dto);

    CategoriaItem categoriaItem = this.service.atualizar(entity, id, idResponsavel);

    return ResponseEntity.ok(mapper.toCategoriaItemDTO(categoriaItem));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> remover(@PathVariable Integer id, @RequestParam int idResponsavel) {
    this.service.deletar(id, idResponsavel);

    return ResponseEntity.noContent().build();
  }
}
