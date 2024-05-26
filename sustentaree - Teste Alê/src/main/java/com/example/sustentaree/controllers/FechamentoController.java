package com.example.sustentaree.controllers;

import com.example.sustentaree.dtos.fechamento.AlterarFechamentoDTO;
import com.example.sustentaree.dtos.fechamento.FechamentoCriacaoDTO;
import com.example.sustentaree.dtos.fechamento.FechamentoListagemDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fechamentos")
@RequiredArgsConstructor
public class FechamentoController {
    @PostMapping
    public ResponseEntity<FechamentoListagemDTO> criar(@RequestBody @Valid FechamentoCriacaoDTO fechamentoCriacaoDTO){
        return null;
    }

    @GetMapping
    public ResponseEntity<List<FechamentoListagemDTO>> listar(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<FechamentoListagemDTO> buscarPoId(@PathVariable Integer id){
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<FechamentoListagemDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AlterarFechamentoDTO alterarFechamentoDTO){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        return ResponseEntity.notFound().build();
    }
}
