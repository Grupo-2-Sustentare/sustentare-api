package com.example.sustentaree.controllers.grafico;

import com.example.sustentaree.domain.grafico.ViewEntradasSaidas;
import com.example.sustentaree.domain.grafico.ViewVencerNaSemana;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.dtos.ComprasDTO;
import com.example.sustentaree.dtos.PerdasPorMesDTO;
import com.example.sustentaree.dtos.ValorEntradasSaidasMesDTO;
import com.example.sustentaree.services.GraficoService;
import com.example.sustentaree.services.InteracaoService;
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

@RestController
@RequestMapping("/graficos")
public class GraficoController {

    private final GraficoService graficoService;

    public GraficoController(GraficoService graficoService) {
        this.graficoService = graficoService;
    }

    @GetMapping("/entradaSaidaMes")
    public ResponseEntity<List<ViewEntradasSaidas>> entradaSaidasMes(){
        List<ViewEntradasSaidas> viewEntradasSaidas = graficoService.entradasSaidas();
        if(viewEntradasSaidas == null){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(viewEntradasSaidas);
    }

    @GetMapping("/vencerNaSemana")
    public ResponseEntity<List<ViewVencerNaSemana>> vencerNaSemana(){
        List<ViewVencerNaSemana> viewVencerNaSemanas = graficoService.vencerNaSemana();
        if(viewVencerNaSemanas == null){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(viewVencerNaSemanas);
    }
    @GetMapping("/valor-entradas-saidas")
    public ResponseEntity<List<ValorEntradasSaidasMesDTO>> getValorEntradasSaidasMes(
        @RequestParam("dataInicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
        @RequestParam("dataFim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,
        @RequestParam(required = false) String categorias,
        @RequestParam(required = false) String itens) {

        try {
            List<ValorEntradasSaidasMesDTO> resultados = graficoService.getValorEntradasSaidasMes(dataInicio, dataFim, categorias, itens);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/regulares-vs-nao-planejadas")
    public ResponseEntity<List<ComprasDTO>> getComprasRegularesVsNaoPlanejadas(
        @RequestParam String dataInicio,
        @RequestParam String dataFim,
        @RequestParam(required = false) String categorias,
        @RequestParam(required = false) String itens) {

        try {
            List<ComprasDTO> resultados = graficoService.getComprasRegularesVsNaoPlanejadas(dataInicio, dataFim, categorias, itens);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/perdas-por-mes")
    public ResponseEntity<List<PerdasPorMesDTO>> getPerdasPorMes(
        @RequestParam String dataInicio,
        @RequestParam String dataFim,
        @RequestParam(required = false) String categorias,
        @RequestParam(required = false) String itens) {

        try {
            List<PerdasPorMesDTO> resultados = graficoService.getPerdasPorMes(dataInicio, dataFim, categorias, itens);
            return ResponseEntity.ok(resultados);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

}
