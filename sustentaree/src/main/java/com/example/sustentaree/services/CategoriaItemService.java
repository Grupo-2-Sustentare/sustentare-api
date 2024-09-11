package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.mapper.CategoriaItemMapper;
import com.example.sustentaree.repositories.CategoriaItemRepository;
import com.example.sustentaree.services.data_structure.HashTable;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaItemService {
  @Autowired
  private final CategoriaItemRepository repository;
  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;

  public CategoriaItemService(CategoriaItemRepository repository) {
    this.repository = repository;
  }

  public List<CategoriaItem> listar() {
    return this.repository.findAll();
  }

  public CategoriaItem porId(Integer id) {
    //    TODO criar exception

    return this.repository.findById(id).orElseThrow(
        () -> new RuntimeException("Categoria de item não encontrada"));
  }

  @Transactional
  public CategoriaItem criar(CategoriaItem categoriaItem, int idResponsavel) {
    //    TODO implementar regras de negócio e exceptions
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    return this.repository.save(categoriaItem);
  }

  @Transactional
  public CategoriaItem atualizar(CategoriaItem categoriaItem, int id, int idResponsavel) {
    //    TODO Exception de conflito
    CategoriaItem categoriaItemAtual = this.porId(id);
    categoriaItem.setId(categoriaItemAtual.getId());

    return this.criar(categoriaItem, idResponsavel);
  }
  @Transactional
  public void deletar(Integer id, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    CategoriaItem categoriaItem = this.porId(id);
    this.repository.delete(categoriaItem);
  }

  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }
}
