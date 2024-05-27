package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.unidade_medida.UnidadeMedidaDTO;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.services.UnidadeMedidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/unidades-medida")
@RequiredArgsConstructor
public class UnidadeMedidaController {

    private final UnidadeMedidaService service;
    @Operation(summary = "Criar uma unidade de medida", description = "Cria uma unidade de medida com base nas informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade de medida criada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            )),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            ))
    })

    @PostMapping
    public ResponseEntity<UnidadeMedidaDTO> criar(@RequestBody @Valid UnidadeMedidaDTO dto) {
        UnidadeMedidaMapper mapper = UnidadeMedidaMapper.INSTANCE;

        UnidadeMedida entity = mapper.toUnidadeMedida(dto);
        UnidadeMedida criada = this.service.criar(entity);

        UnidadeMedidaDTO nova = mapper.toUnidadeMedidaDTO(criada);
        return ResponseEntity.ok(nova);
    }

    @Operation(summary = "Listar unidades de medida", description = "Lista todas as unidades de medida cadastradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidades de medida listadas com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Nenhuma unidade de medida encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            ))
    })

    @GetMapping
    public ResponseEntity<List<UnidadeMedidaDTO>> listar() {
        List<UnidadeMedida> lista = this.service.listar();

        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UnidadeMedidaMapper mapper = UnidadeMedidaMapper.INSTANCE;
        return ResponseEntity.ok(mapper.toUnidadeMedidaListDTO(lista));
    }

    @Operation(summary = "Buscar uma unidade de medida por ID", description = "Retorna uma unidade de medida com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade de medida retornada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Unidade de medida não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            ))
    })

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeMedidaDTO> buscarUnidadeMedidaPorId(@PathVariable Integer id) {
        UnidadeMedida unidadeMedida = this.service.porId(id);

        UnidadeMedidaMapper mapper = UnidadeMedidaMapper.INSTANCE;
        UnidadeMedidaDTO response = mapper.toUnidadeMedidaDTO(unidadeMedida);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar uma unidade de medida", description = "Atualiza uma unidade de medida com base nas informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unidade de medida atualizada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Unidade de medida não encontrada", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UnidadeMedidaDTO.class)
            ))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UnidadeMedidaDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid UnidadeMedidaDTO dto) {
        UnidadeMedidaMapper mapper = UnidadeMedidaMapper.INSTANCE;
        UnidadeMedida unidadeMedida = mapper.toUnidadeMedida(dto);

        UnidadeMedida atualizada = this.service.atualizar(unidadeMedida, id);
        UnidadeMedidaDTO response = mapper.toUnidadeMedidaDTO(atualizada);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remover uma unidade de medida", description = "Remove uma unidade de medida com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Unidade de medida removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Unidade de medida não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUnidadeMedida(@PathVariable Integer id) {
        this.service.deletar(id);
        return ResponseEntity.notFound().build();
    }
}
