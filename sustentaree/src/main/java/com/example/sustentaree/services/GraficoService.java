package com.example.sustentaree.services;

import com.example.sustentaree.domain.grafico.ViewEntradasSaidas;
import com.example.sustentaree.repositories.ViewEntradasSaidasRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GraficoService {
    private final ViewEntradasSaidasRepository viewEntradasSaidasRepository;

    public GraficoService(ViewEntradasSaidasRepository viewEntradasSaidasRepository) {
        this.viewEntradasSaidasRepository = viewEntradasSaidasRepository;
    }

    public List<ViewEntradasSaidas> entradasSaidas() {
        List<ViewEntradasSaidas> viewEntradasSaidas = viewEntradasSaidasRepository.findAll();
        return viewEntradasSaidas;
    }

}
