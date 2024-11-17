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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/unidades-medida")
public class UnidadeMedidaController {
  @Autowired
  private final UnidadeMedidaService service;

  public UnidadeMedidaController(UnidadeMedidaService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<UnidadeMedidaDTO> criar(@RequestBody @Valid UnidadeMedidaDTO dto, @RequestParam int idResponsavel) {
    UnidadeMedidaMapper mapper = UnidadeMedidaMapper.INSTANCE;

    UnidadeMedida entity = mapper.toUnidadeMedida(dto);
    UnidadeMedida criada = this.service.criar(entity, idResponsavel);

    UnidadeMedidaDTO nova = mapper.toUnidadeMedidaDTO(criada);
    return ResponseEntity.ok(nova);
  }

  @GetMapping
  public ResponseEntity<List<UnidadeMedidaDTO>> listar() {
    List<UnidadeMedida> lista = this.service.listar();

    if (lista.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    UnidadeMedidaMapper mapper = UnidadeMedidaMapper.INSTANCE;
    return ResponseEntity.ok(mapper.toUnidadeMedidaListDTO(lista));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UnidadeMedidaDTO> buscarUnidadeMedidaPorId(@PathVariable Integer id) {
    UnidadeMedida unidadeMedida = this.service.porId(id);

    UnidadeMedidaMapper mapper = UnidadeMedidaMapper.INSTANCE;
    UnidadeMedidaDTO response = mapper.toUnidadeMedidaDTO(unidadeMedida);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UnidadeMedidaDTO> atualizar
      (
          @PathVariable Integer id,
          @RequestBody @Valid UnidadeMedidaDTO dto,
          @RequestParam int idResponsavel
      ) {
    UnidadeMedidaMapper mapper = UnidadeMedidaMapper.INSTANCE;
    UnidadeMedida unidadeMedida = mapper.toUnidadeMedida(dto);

    UnidadeMedida atualizada = this.service.atualizar(unidadeMedida, id, idResponsavel);
    UnidadeMedidaDTO response = mapper.toUnidadeMedidaDTO(atualizada);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removerUnidadeMedida
      (
          @PathVariable Integer id,
          @RequestParam int idResponsavel
      ) {
    this.service.deletar(id, idResponsavel);
    return ResponseEntity.noContent().build();
  }
}
