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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Operation(summary = "Criar um item", description = "Cria um item com base nas informações fornecidas")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item criado com sucesso", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        )),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        )),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        ))
})
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
@Operation(summary = "Listar itens", description = "Lista todos os itens cadastrados")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de itens retornada com sucesso", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        )),
        @ApiResponse(responseCode = "404", description = "Nenhum item cadastrado", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        )),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        ))
})
    @GetMapping
    public ResponseEntity<List<ItemDTO>> listar() {
        List<Item> items = itemRepository.findAll();
        if (items.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ItemMapper.INSTANCE.toItemList(items));
    }

@Operation(summary = "Buscar item por ID", description = "Retorna um item com base no ID fornecido")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item retornado com sucesso", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        )),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        )),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        ))
})
    @GetMapping("/{id}")
    public ResponseEntity<ItemDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Item> itemOptional = itemRepository.findById(id);
        if (itemOptional.isPresent()) {
            return ResponseEntity.ok(ItemMapper.INSTANCE.toItemDTO(itemOptional.get()));
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Atualizar um item", description = "Atualiza um item com base nas informações fornecidas")
@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ItemDTO.class)
            ))
    })

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
@Operation(summary = "Remover um item", description = "Remove um item com base no ID fornecido")
@ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Item removido com sucesso", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        )),
        @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        )),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ItemDTO.class)
        ))
})
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
