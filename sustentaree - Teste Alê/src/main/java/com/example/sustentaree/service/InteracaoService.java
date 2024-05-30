package com.example.sustentaree.service;

import com.example.sustentaree.repositories.InteracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteracaoService {
    @Autowired
    private final InteracaoRepository repository;

    public InteracaoService(InteracaoRepository repository) {
        this.repository = repository;
    }
}
