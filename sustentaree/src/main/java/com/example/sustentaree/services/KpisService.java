package com.example.sustentaree.services;

import com.example.sustentaree.domain.grafico.ViewItemParado;
import com.example.sustentaree.repositories.ViewItemParadoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KpisService {

    private final ViewItemParadoRepository itemParadoRepository;

    public KpisService(ViewItemParadoRepository itemParadoRepository) {
        this.itemParadoRepository = itemParadoRepository;
    }

    public List<ViewItemParado> itemParado() {
        List<ViewItemParado> itens = itemParadoRepository.findAll();
        return itens;
    }
}
