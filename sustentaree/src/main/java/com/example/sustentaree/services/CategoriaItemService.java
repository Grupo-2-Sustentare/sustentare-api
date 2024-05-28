package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.mapper.CategoriaItemMapper;
import com.example.sustentaree.repositories.CategoriaItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaItemService {
    @Autowired
    private final CategoriaItemRepository repository;

    public CategoriaItemService(CategoriaItemRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaItem> listar() {
        return this.repository.findAll();
    }

    public CategoriaItem porId(Integer id) {
    //        TODO criar exception

        return this.repository.findById(id).orElseThrow(
                () -> new RuntimeException("Categoria de item não encontrada"));
    }

    public CategoriaItem criar(CategoriaItem categoriaItem) {
    //    TODO implementar regras de negócio e exceptions
        return this.repository.save(categoriaItem);
    }

    public CategoriaItem atualizar(CategoriaItem categoriaItem, int id) {
    //    TODO Exception de conflito
        CategoriaItem categoriaItemAtual = this.porId(id);
        categoriaItem.setId(categoriaItemAtual.getId());

        return this.criar(categoriaItem);
    }
    public void deletar(Integer id) {
        CategoriaItem categoriaItem = this.porId(id);
        this.repository.delete(categoriaItem);
    }
}
