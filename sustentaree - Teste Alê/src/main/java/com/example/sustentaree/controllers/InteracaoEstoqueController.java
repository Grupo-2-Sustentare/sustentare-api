package com.example.sustentaree.controllers;

import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueCriacaoDTO;
import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueListagemDTO;
import com.example.sustentaree.service.InteracaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interacoes-estoque")
public class InteracaoEstoqueController {

    @Autowired
    private InteracaoService service;

    public InteracaoEstoqueController(InteracaoService service) {
        this.service = service;
    }

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
