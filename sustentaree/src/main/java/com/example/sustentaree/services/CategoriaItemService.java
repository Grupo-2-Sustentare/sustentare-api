package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.repositories.CategoriaItemRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaItemService {
  @Autowired
  private final CategoriaItemRepository repository;
  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;
  @Autowired
  private ItemValidationService itemValidationService;

  public CategoriaItemService(CategoriaItemRepository repository) {
    this.repository = repository;
  }

  public List<CategoriaItem> listar() {
    return this.repository.findByAtivoTrueOrderByNome();
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
  public List<Item> deletar(Integer id, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    List<Item> itens = this.itemValidationService.listByCategoriaItem(id, this);
    if (!itens.isEmpty()) {
      System.out.println("CategoriaItem não pode ser deletada pois está associada a um ou mais itens");
      return itens;
    }
    this.repository.updateAtivoById(false, id);
    return itens;
  }



  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }

  public CategoriaItem getCategoriaByName(String nome) {
    return this.repository.findByNome(nome);
  }
}
