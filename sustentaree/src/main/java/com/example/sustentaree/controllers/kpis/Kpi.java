package com.example.sustentaree.controllers.kpis;

import com.example.sustentaree.domain.grafico.ViewItemParado;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.dtos.KpiComprasNaoPlanejadasDTO;
import com.example.sustentaree.dtos.KpiPerdasDto;
import com.example.sustentaree.dtos.KpiValorTotalEntradasDTO;
import com.example.sustentaree.dtos.KpiValorTotalSaidasDTO;
import com.example.sustentaree.services.InteracaoService;
import com.example.sustentaree.services.ItemService;
import com.example.sustentaree.services.KpisService;
import com.example.sustentaree.services.ProdutoService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Map;

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

//    @GetMapping("/itemMaisAntigo")
//    public ResponseEntity<List<ViewItemParado>> getKpiItemMaisAntigo() {
//        List<ViewItemParado> itens = kpisService.itemParado();
//        if (itens.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
//        }
//        return ResponseEntity.ok(itens);
//    }
//
//    @GetMapping("/ultimaAdicao")
//    public ResponseEntity<InteracaoEstoque> getKpiUltimaAdicao() {
//        InteracaoEstoque interacaoEstoque = interacaoService.kpiUltimaAdicaoEstoque();
//        if (interacaoEstoque == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(interacaoEstoque);
//    }
//
//    @GetMapping("/maiorRetirada")
//    public ResponseEntity<List<InteracaoEstoque>> getKpiMaiorRetirada() {
//        List<InteracaoEstoque> interacaoEstoque = interacaoService.kpiMaiorRetirada();
//        if (interacaoEstoque == null) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        return ResponseEntity.ok(interacaoEstoque);
//    }
//
//    @GetMapping("/vencimento")
//    public ResponseEntity<List<Item>> getKpiVencimento() {
//        List<Item> interacaoEstoques = itemService.kpiVencimento();
//        if (interacaoEstoques.isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
//        }
//        return ResponseEntity.ok(interacaoEstoques);
//    }

//    @GetMapping("/perdas")
//    public ResponseEntity<KpiPerdasDto> getKpiPerdas(
//        @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
//        @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,
//        @RequestParam(required = false) String categorias,
//        @RequestParam(required = false) String itens) {
//
//        try {
//            KpiPerdasDto resultado = kpisService.getKpiPerdas(dataInicio, dataFim, categorias, itens);
//            return ResponseEntity.ok(resultado);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }

    @GetMapping("/compras-nao-planejadas")
    public ResponseEntity<KpiComprasNaoPlanejadasDTO> getKpiComprasNaoPlanejadas(
        @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
        @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,
        @RequestParam(required = false) String categorias,
        @RequestParam(required = false) String itens) {

        try {
            KpiComprasNaoPlanejadasDTO resultado = kpisService.getKpiComprasNaoPlanejadas(dataInicio, dataFim, categorias, itens);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/valor-total-entradas")
    public ResponseEntity<KpiValorTotalEntradasDTO> getKpiValorTotalEntradas(
        @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
        @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,
        @RequestParam(required = false) String categorias,
        @RequestParam(required = false) String itens) {

        try {
            KpiValorTotalEntradasDTO resultado = kpisService.getKpiValorTotalEntradas(dataInicio, dataFim, categorias, itens);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/valor-total-saidas")
    public ResponseEntity<KpiValorTotalSaidasDTO> getKpiValorTotalSaidas(
        @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
        @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,
        @RequestParam(required = false) String categorias,
        @RequestParam(required = false) String itens) {

        try {
            KpiValorTotalSaidasDTO resultado = kpisService.getKpiValorTotalSaidas(dataInicio, dataFim, categorias, itens);
            return ResponseEntity.ok(resultado);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();  // Tratar erros
        }
    }

}
