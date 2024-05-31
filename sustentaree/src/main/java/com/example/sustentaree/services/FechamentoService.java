package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.exception.EntidadeNaoEncontradaException;
import com.example.sustentaree.repositories.FechamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FechamentoService {
    @Autowired
    private final FechamentoRepository repository;

    public FechamentoService(FechamentoRepository repository) {
        this.repository = repository;
    }

    public List<Fechamento> listar() {
        return this.repository.findAll();
    }

    public Fechamento porId(Integer id) {
        Optional<Fechamento> fechamentoOpt =
                repository.findById(id);

        if (fechamentoOpt.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Fechamento");
        }

        return fechamentoOpt.get();
    }

    public Fechamento criar(Fechamento fechamento) {
        if (fechamento == null){
            throw new IllegalArgumentException("Não foi passado um Fechamento");
        }
        return repository.save(fechamento);
    }

    public Fechamento atualizar(Fechamento fechamento, int id) {
        //    TODO Exception de conflito
        Fechamento fechamentoAtual = this.porId(id);
        return this.criar(fechamentoAtual);
    }
    public void deletar(Integer id) {
        Fechamento fechamento = this.porId(id);
        this.repository.delete(fechamento);
    }
}
