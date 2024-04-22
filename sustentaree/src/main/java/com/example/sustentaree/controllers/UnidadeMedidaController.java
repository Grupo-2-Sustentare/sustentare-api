package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.unidade_medida.UnidadeMedidaDTO;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.repositories.UnidadeMedidaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/unidades-medida")
public class UnidadeMedidaController {

    private final UnidadeMedidaRepository unidadeMedidaRepository;

    public UnidadeMedidaController(UnidadeMedidaRepository unidadeMedidaRepository) {
        this.unidadeMedidaRepository = unidadeMedidaRepository;
    }
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
    public ResponseEntity<UnidadeMedidaDTO> criarUnidadeMedida(@RequestBody UnidadeMedidaDTO dto) {
        System.out.println(dto.getConversaoPadrao());
        UnidadeMedida unidadeMedida = UnidadeMedidaMapper.INSTANCE.toUnidadeMedida(dto);
        UnidadeMedida unidadeMedidaSalvo = unidadeMedidaRepository.save(unidadeMedida);
        UnidadeMedidaDTO unidadeMedidaDTO = UnidadeMedidaMapper.INSTANCE.toUnidadeMedidaDTO(unidadeMedidaSalvo);
        return ResponseEntity.ok(unidadeMedidaDTO);
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
        List<UnidadeMedida> unidadesMedida = unidadeMedidaRepository.findAll();
        if (unidadesMedida.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UnidadeMedidaDTO> unidadeMedidasDTO = UnidadeMedidaMapper.INSTANCE.toUnidadeMedidaListDTO(unidadesMedida);
        return ResponseEntity.ok(unidadeMedidasDTO);
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
        Optional<UnidadeMedida> unidadeMedida = unidadeMedidaRepository.findById(id);
        if (unidadeMedida.isPresent()) {
            UnidadeMedidaDTO unidadeMedidaDTO = UnidadeMedidaMapper.INSTANCE.toUnidadeMedidaDTO(unidadeMedida.get());
            return ResponseEntity.ok(unidadeMedidaDTO);
        }
        return ResponseEntity.notFound().build();
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
    public ResponseEntity<UnidadeMedidaDTO> atualizarUnidadeMedida(@PathVariable Integer id, @RequestBody UnidadeMedidaDTO dto) {
        Optional<UnidadeMedida> unidadeMedidaOptional = unidadeMedidaRepository.findById(id);
        if (unidadeMedidaOptional.isPresent()) {
            UnidadeMedida unidadeMedida = UnidadeMedidaMapper.INSTANCE.toUnidadeMedida(dto);
            unidadeMedida.setId(unidadeMedidaOptional.get().getId());
            unidadeMedidaRepository.save(unidadeMedida);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Remover uma unidade de medida", description = "Remove uma unidade de medida com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Unidade de medida removida com sucesso", content = @Content(
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
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerUnidadeMedida(@PathVariable Integer id) {
        Optional<UnidadeMedida> unidadeMedida = unidadeMedidaRepository.findById(id);
        if (unidadeMedida.isPresent()) {
            unidadeMedidaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}





