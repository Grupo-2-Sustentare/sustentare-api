package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.dtos.categoria.CategoriaItemDTO;
import com.example.sustentaree.mapper.ItemCategoriaMapper;
import com.example.sustentaree.repositories.CategoriaRepository;
import io.swagger.v3.oas.annotations.links.Link;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.responses.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private CategoriaRepository categoriaRepository;

    public CategoriaController(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }
    @Operation(summary = "Criar uma categoria", description = "Cria uma categoria com base nas informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class),
                    examples = @ExampleObject(
                            value = "{\n  \"nome\": \"Categoria Exemplo\",\n  \"descricao\": \"Descrição Exemplo\"\n}"
                    )
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            ))
    })
    @PostMapping
    public ResponseEntity<CategoriaItemDTO> criar(
            @RequestBody @Parameter(
                    description = "Informações da categoria a ser criada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CategoriaItemDTO.class)
                    ),
                    examples = @ExampleObject(
                            value = "{\n  \"nome\": \"Categoria Exemplo\",\n  \"descricao\": \"Descrição Exemplo\"\n}"
                    )
            ) CategoriaItemDTO categoriaItemDTO) {

        System.out.println(categoriaItemDTO.getNome());
        CategoriaItem categoriaItem = ItemCategoriaMapper.INSTANCE.toCategoriaItem(categoriaItemDTO);
        categoriaRepository.save(categoriaItem);
        return ResponseEntity.ok(ItemCategoriaMapper.INSTANCE.toCategoriaItemDTO(categoriaItem));
    }
    @Operation(summary = "Obter todas as categorias", description = "Retorna uma lista com todas as categorias cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Nenhuma categoria encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            ))
    })
    @GetMapping
    public ResponseEntity<List<CategoriaItemDTO>> findAll() {
        List<CategoriaItem> categoriaItems = categoriaRepository.findAll();
        if (categoriaItems.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<CategoriaItemDTO> categoriasDTO = ItemCategoriaMapper.INSTANCE.toCategoriaItemListDTO(categoriaItems);
        return ResponseEntity.ok(categoriasDTO);

   }
    @Operation(summary = "Obter uma categoria por ID", description = "Retorna uma categoria com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria retornada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            ))
    })
   @GetMapping("/{id}")
    public ResponseEntity<CategoriaItemDTO> buscarPorID(@PathVariable Integer id) {
        Optional<CategoriaItem> categoria = categoriaRepository.findById(id);
        if (categoria.isPresent()) {
            return ResponseEntity.ok(ItemCategoriaMapper.INSTANCE.toCategoriaItemDTO(categoria.get()));
        }
        return ResponseEntity.notFound().build();
   }
    @Operation(summary = "Atualizar uma categoria", description = "Atualiza uma categoria com base nas informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            ))
    })
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
    @Operation(summary = "Remover uma categoria", description = "Remove uma categoria com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = CategoriaItemDTO.class)
            ))
    })
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
