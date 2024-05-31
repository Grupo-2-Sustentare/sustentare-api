package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.dtos.fechamento.AlterarFechamentoDTO;
import com.example.sustentaree.dtos.fechamento.FechamentoCriacaoDTO;
import com.example.sustentaree.dtos.fechamento.FechamentoListagemDTO;
import com.example.sustentaree.mapper.FechamentoMapper;
import com.example.sustentaree.services.FechamentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fechamentos")
public class FechamentoController {

    @Autowired
    private final FechamentoService service;

    public FechamentoController(FechamentoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<FechamentoListagemDTO> criar(@RequestBody @Valid FechamentoCriacaoDTO fechamentoCriacaoDTO){
        FechamentoMapper mapper = FechamentoMapper.INSTANCE;
        Fechamento entity = mapper.toFechamento(fechamentoCriacaoDTO);
        Fechamento novo = this.service.criar(entity);

        FechamentoListagemDTO response = mapper.toFechamentoListagemDTO(novo);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FechamentoListagemDTO>> listar(){
        List<Fechamento> fechamentos = this.service.listar();

        FechamentoMapper mapper = FechamentoMapper.INSTANCE;
        return ResponseEntity.ok(mapper.toFechamentoList(fechamentos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FechamentoListagemDTO> buscarPoId(@PathVariable Integer id){
        Fechamento fechamento = this.service.porId(id);

        FechamentoMapper mapper = FechamentoMapper.INSTANCE;
        return ResponseEntity.ok(mapper.toFechamentoListagemDTO(fechamento));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FechamentoListagemDTO> atualizar(@PathVariable Integer id, @RequestBody @Valid AlterarFechamentoDTO alterarFechamentoDTO){
        FechamentoMapper mapper = FechamentoMapper.INSTANCE;
        Fechamento entity = mapper.toFechamento(alterarFechamentoDTO);

        Fechamento fechamento = this.service.atualizar(entity, id);

        return ResponseEntity.ok(mapper.toFechamentoListagemDTO(fechamento));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        this.service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
