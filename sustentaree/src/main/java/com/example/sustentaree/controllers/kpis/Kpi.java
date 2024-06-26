package com.example.sustentaree.controllers.kpis;

import com.example.sustentaree.domain.grafico.ViewItemParado;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.services.InteracaoService;
import com.example.sustentaree.services.ItemService;
import com.example.sustentaree.services.KpisService;
import com.example.sustentaree.services.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/kpis")
public class Kpi {
    private final ItemService itemService;
    private final InteracaoService interacaoService;
    private final KpisService kpisService;

    public Kpi(ItemService itemService, InteracaoService interacaoService, KpisService kpisService) {
        this.itemService = itemService;
        this.interacaoService = interacaoService;
        this.kpisService = kpisService;
    }

    @GetMapping("/itemMaisAntigo")
    public ResponseEntity<List<ViewItemParado>> getKpiItemMaisAntigo() {
        List<ViewItemParado> itens = kpisService.itemParado();
        if (itens.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(itens);
    }

    @GetMapping("/ultimaAdicao")
    public ResponseEntity<InteracaoEstoque> getKpiUltimaAdicao() {
        InteracaoEstoque interacaoEstoque = interacaoService.kpiUltimaAdicaoEstoque();
        if (interacaoEstoque == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(interacaoEstoque);
    }

    @GetMapping("/maiorRetirada")
    public ResponseEntity<List<InteracaoEstoque>> getKpiMaiorRetirada() {
        List<InteracaoEstoque> interacaoEstoque = interacaoService.kpiMaiorRetirada();
        if (interacaoEstoque == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(interacaoEstoque);
    }

    @GetMapping("/vencimento")
    public ResponseEntity<List<Item>> getKpiVencimento() {
        List<Item> interacaoEstoques = itemService.kpiVencimento();
        if (interacaoEstoques.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(interacaoEstoques);
    }

}
