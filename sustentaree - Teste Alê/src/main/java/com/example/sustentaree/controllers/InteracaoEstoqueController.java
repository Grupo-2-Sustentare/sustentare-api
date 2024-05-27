package com.example.sustentaree.controllers;

import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueCriacaoDTO;
import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueListagemDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interacoes-estoque")
@RequiredArgsConstructor
public class InteracaoEstoqueController {
    @PostMapping
    public ResponseEntity<InteracaoEstoqueListagemDTO> criar(@RequestBody @Valid InteracaoEstoqueCriacaoDTO interacaoEstoqueCriacaoDTO){
        return null;
    }

    @GetMapping
    public ResponseEntity<List<InteracaoEstoqueListagemDTO>> listar(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<InteracaoEstoqueListagemDTO> buscarPoId(@PathVariable Integer id){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        return ResponseEntity.notFound().build();
    }
}
