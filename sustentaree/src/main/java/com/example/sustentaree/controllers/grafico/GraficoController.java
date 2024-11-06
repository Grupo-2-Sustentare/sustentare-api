package com.example.sustentaree.controllers.grafico;

import com.example.sustentaree.domain.grafico.ViewEntradasSaidas;
import com.example.sustentaree.domain.grafico.ViewVencerNaSemana;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.dtos.*;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/graficos")
public class GraficoController {

    private final GraficoService graficoService;

    public GraficoController(GraficoService graficoService) {
        this.graficoService = graficoService;
    }

//    @GetMapping("/entradaSaidaMes")
//    public ResponseEntity<List<ViewEntradasSaidas>> entradaSaidasMes(){
//        List<ViewEntradasSaidas> viewEntradasSaidas = graficoService.entradasSaidas();
//        if(viewEntradasSaidas == null){
//            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
//        }
//        return ResponseEntity.ok(viewEntradasSaidas);
//    }
//
//    @GetMapping("/vencerNaSemana")
//    public ResponseEntity<List<ViewVencerNaSemana>> vencerNaSemana(){
//        List<ViewVencerNaSemana> viewVencerNaSemanas = graficoService.vencerNaSemana();
//        if(viewVencerNaSemanas == null){
//            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
//        }
//        return ResponseEntity.ok(viewVencerNaSemanas);
//    }
    @GetMapping("/valor-entradas-saidas")
    public ResponseEntity<List<ValorEntradasSaidasMesDTO>> getValorEntradasSaidasMes(
        @RequestParam("dataInicio") LocalDate dataInicio,
        @RequestParam("dataFim") LocalDate dataFim,
        @RequestParam(required = false) String categorias,
        @RequestParam(required = false) String itens
    ) throws Exception {
         List<ValorEntradasSaidasMesDTO> resultados = graficoService.getValorEntradasSaidasMes(dataInicio, dataFim, categorias, itens);
         return ResponseEntity.ok(resultados);
    }
    @GetMapping("/regulares-vs-nao-planejadas")
    public ResponseEntity<List<ComprasDTO>> getComprasRegularesVsNaoPlanejadas(
            @RequestParam("dataInicio") LocalDate dataInicio,
            @RequestParam("dataFim") LocalDate dataFim,
            @RequestParam(required = false) String categorias,
            @RequestParam(required = false) String itens
    ) throws Exception {
        List<ComprasDTO> resultados = graficoService.getComprasRegularesVsNaoPlanejadas(dataInicio, dataFim, categorias, itens);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/perdas-por-mes")
    public ResponseEntity<List<PerdasPorMesDTO>> getPerdasPorMes(
        @RequestParam("dataInicio") LocalDate dataInicio,
        @RequestParam("dataFim") LocalDate dataFim,
        @RequestParam(required = false) String categorias,
        @RequestParam(required = false) String itens
    ) throws Exception {
        List<PerdasPorMesDTO> resultados = graficoService.getPerdasPorMes(dataInicio, dataFim, categorias, itens);
        return ResponseEntity.ok(resultados);
    }

    @GetMapping("/colaboradores/log-operacoes")
    public ResponseEntity<List<AuditoriaColaboradoresDTO>> getLogOperacoes(
        @RequestParam("dataInicio") LocalDate dataInicio,
        @RequestParam("dataFim") LocalDate dataFim,
        @RequestParam(required = false) String colaboradores
    ) throws Exception {
        List<AuditoriaColaboradoresDTO> auditoriaColaboradores = graficoService.getAuditoriaColaboradores(dataInicio, dataFim, colaboradores);
        return ResponseEntity.ok(auditoriaColaboradores);
    }

    @GetMapping("/colaboradores/entrada-saida")
    public ResponseEntity<List<EntradasSaidasColaboradoresDTO>> getEntradaSaida(
        @RequestParam("dataInicio") LocalDate dataInicio,
        @RequestParam("dataFim") LocalDate dataFim,
        @RequestParam(required = false) String colaboradores
    ) throws Exception {
        List<EntradasSaidasColaboradoresDTO> entradasSaidasColaboradores = graficoService.getEntradasSaidasPorColaborador(dataInicio, dataFim, colaboradores);
        return ResponseEntity.ok(entradasSaidasColaboradores);
    }

}
