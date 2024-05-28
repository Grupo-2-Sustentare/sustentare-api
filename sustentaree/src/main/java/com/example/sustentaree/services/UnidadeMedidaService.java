package com.example.sustentaree.services;

import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.unidade_medida.UnidadeMedidaDTO;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.repositories.UnidadeMedidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadeMedidaService {
    @Autowired
    private final UnidadeMedidaRepository repository;

    public UnidadeMedidaService(UnidadeMedidaRepository repository) {
        this.repository = repository;
    }

    public List<UnidadeMedida> listar() {
        return this.repository.findAll();
    }
    //            TODO criar exception
    public UnidadeMedida porId(Integer id) {
        return this.repository.findById(id).orElseThrow(
                () -> new RuntimeException("Unidade de medida não encontrada"));
    }
    //            TODO implementar regras de negócio e exceptions
    public UnidadeMedida criar(UnidadeMedida unidadeMedida) {
        return this.repository.save(unidadeMedida);
    }
    //        TODO Criar validações sobre conflitos e suas exceptions
    public UnidadeMedida atualizar(UnidadeMedida unidadeMedida, int id) {
        UnidadeMedida unidadeMedidaAtual = this.porId(id);
        unidadeMedida.setId(unidadeMedidaAtual.getId());
        return this.repository.save(unidadeMedida);
    }
    public void deletar(Integer id) {
        UnidadeMedida unidadeMedida = this.porId(id);
        this.repository.delete(unidadeMedida);
    }
}
