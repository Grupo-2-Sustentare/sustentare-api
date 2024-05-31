package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueCriacaoDTO;
import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueListagemDTO;
import com.example.sustentaree.dtos.item.ItemListagemDTO;
import com.example.sustentaree.mapper.FechamentoMapper;
import com.example.sustentaree.mapper.InteracaoEstoqueMapper;
import com.example.sustentaree.mapper.ItemMapper;
import com.example.sustentaree.services.InteracaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interacoes-estoque")
public class InteracaoEstoqueController {

    @Autowired
    private final InteracaoService service;

    public InteracaoEstoqueController(InteracaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InteracaoEstoqueListagemDTO> criar(@RequestBody @Valid InteracaoEstoqueCriacaoDTO interacaoEstoqueCriacaoDTO,
                                                             @RequestParam Integer idProduto,
                                                             @RequestParam Integer fkFechamento){
        InteracaoEstoqueMapper mapper = InteracaoEstoqueMapper.INSTANCE;
        InteracaoEstoque interacaoEstoque = mapper.toInteracaoEstoque(interacaoEstoqueCriacaoDTO);
        InteracaoEstoque interacaoEstoqueNovo = this.service.criar(interacaoEstoque, idProduto, fkFechamento);
        InteracaoEstoqueListagemDTO response = mapper.toInteracaoEstoqueListagemDTO(interacaoEstoqueNovo);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<InteracaoEstoqueListagemDTO>> listar(){
        List<InteracaoEstoque> interacaoEstoques = this.service.listar();

        InteracaoEstoqueMapper mapper = InteracaoEstoqueMapper.INSTANCE;
        return ResponseEntity.ok(mapper.toInteracaoEstoqueList(interacaoEstoques));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InteracaoEstoqueListagemDTO> buscarPoId(@PathVariable Integer id){
        InteracaoEstoque interacaoEstoque = this.service.porId(id);

        InteracaoEstoqueMapper mapper = InteracaoEstoqueMapper.INSTANCE;
        return ResponseEntity.ok(mapper.toInteracaoEstoqueListagemDTO(interacaoEstoque));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Integer id) {
        this.service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
