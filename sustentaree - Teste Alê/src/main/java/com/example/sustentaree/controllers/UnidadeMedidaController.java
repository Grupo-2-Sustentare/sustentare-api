package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.unidade_medida.UnidadeMedidaDTO;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.repositories.UnidadeMedidaRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.Valid;
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

    @PostMapping
    public ResponseEntity<UnidadeMedidaDTO> criarUnidadeMedida(@RequestBody @Valid UnidadeMedidaDTO dto) {
        System.out.println(dto.getConversaoPadrao());
        UnidadeMedida unidadeMedida = UnidadeMedidaMapper.INSTANCE.toUnidadeMedida(dto);
        UnidadeMedida unidadeMedidaSalvo = unidadeMedidaRepository.save(unidadeMedida);
        UnidadeMedidaDTO unidadeMedidaDTO = UnidadeMedidaMapper.INSTANCE.toUnidadeMedidaDTO(unidadeMedidaSalvo);
        return ResponseEntity.ok(unidadeMedidaDTO);
    }

    @GetMapping
    public ResponseEntity<List<UnidadeMedidaDTO>> listar() {
        List<UnidadeMedida> unidadesMedida = unidadeMedidaRepository.findAll();
        if (unidadesMedida.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<UnidadeMedidaDTO> unidadeMedidasDTO = UnidadeMedidaMapper.INSTANCE.toUnidadeMedidaListDTO(unidadesMedida);
        return ResponseEntity.ok(unidadeMedidasDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UnidadeMedidaDTO> buscarUnidadeMedidaPorId(@PathVariable Integer id) {
        Optional<UnidadeMedida> unidadeMedida = unidadeMedidaRepository.findById(id);
        if (unidadeMedida.isPresent()) {
            UnidadeMedidaDTO unidadeMedidaDTO = UnidadeMedidaMapper.INSTANCE.toUnidadeMedidaDTO(unidadeMedida.get());
            return ResponseEntity.ok(unidadeMedidaDTO);
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<UnidadeMedidaDTO> atualizarUnidadeMedida(@PathVariable Integer id, @RequestBody @Valid UnidadeMedidaDTO dto) {
        Optional<UnidadeMedida> unidadeMedidaOptional = unidadeMedidaRepository.findById(id);
        if (unidadeMedidaOptional.isPresent()) {
            UnidadeMedida unidadeMedida = UnidadeMedidaMapper.INSTANCE.toUnidadeMedida(dto);
            unidadeMedida.setId(unidadeMedidaOptional.get().getId());
            unidadeMedidaRepository.save(unidadeMedida);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }


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





