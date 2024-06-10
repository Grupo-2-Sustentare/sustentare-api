package com.example.sustentaree.services;

import com.example.sustentaree.domain.grafico.ViewEntradasSaidas;
import com.example.sustentaree.domain.grafico.ViewVencerNaSemana;
import com.example.sustentaree.repositories.ViewEntradasSaidasRepository;
import com.example.sustentaree.repositories.ViewVencerNaSemanaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraficoService {
    private final ViewEntradasSaidasRepository viewEntradasSaidasRepository;
    private final ViewVencerNaSemanaRepository viewVencerNaSemanaRepository;

    public GraficoService(ViewEntradasSaidasRepository viewEntradasSaidasRepository, ViewVencerNaSemanaRepository viewVencerNaSemanaRepository) {
        this.viewEntradasSaidasRepository = viewEntradasSaidasRepository;
        this.viewVencerNaSemanaRepository = viewVencerNaSemanaRepository;
    }

    public List<ViewEntradasSaidas> entradasSaidas() {
        List<ViewEntradasSaidas> viewEntradasSaidas = viewEntradasSaidasRepository.findAll();
        return viewEntradasSaidas;
    }

    public List<ViewVencerNaSemana> vencerNaSemana() {
        List<ViewVencerNaSemana> viewVencerNaSemanas = viewVencerNaSemanaRepository.findAll();
        return viewVencerNaSemanas;
    }

}
