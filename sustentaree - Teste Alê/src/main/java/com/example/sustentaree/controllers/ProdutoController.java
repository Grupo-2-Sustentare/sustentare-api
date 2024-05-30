package com.example.sustentaree.controllers;

import com.example.sustentaree.dtos.produto.AlterarProdutoDTO;
import com.example.sustentaree.dtos.produto.ProdutoCriacaoDTO;
import com.example.sustentaree.dtos.produto.ProdutoListagemDTO;
import com.example.sustentaree.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProdutoListagemDTO> criar(@RequestBody @Valid ProdutoCriacaoDTO produtoCriacaoDTO){
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoListagemDTO>> listar(){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> buscarPoId(@PathVariable Integer id){
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoListagemDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AlterarProdutoDTO alterarProdutoDTO){
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        return ResponseEntity.notFound().build();
    }
}
