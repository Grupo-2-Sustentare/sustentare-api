package com.example.sustentaree.controllers.grafico;

import com.example.sustentaree.domain.grafico.ViewEntradasSaidas;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.services.GraficoService;
import com.example.sustentaree.services.InteracaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/graficos")
public class GraficoController {

    private final GraficoService graficoService;  ;

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
}
