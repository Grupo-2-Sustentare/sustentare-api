package com.example.sustentaree.service;

import com.example.sustentaree.repositories.FechamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FechamentoService {
    @Autowired
    private final FechamentoRepository repository;

    public FechamentoService(FechamentoRepository repository) {
        this.repository = repository;
    }
}
