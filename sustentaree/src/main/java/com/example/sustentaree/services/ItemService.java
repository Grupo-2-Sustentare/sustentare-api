package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.mapper.ItemMapper;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ItemService {
  @Autowired
  private final ItemRepository repository;
  @Autowired
  private final UnidadeMedidaService unidadeMedidaService;
  @Autowired
  private final CategoriaItemService categoriaItemService;
  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;

  public ItemService(ItemRepository repository, UnidadeMedidaService unidadeMedidaService, CategoriaItemService categoriaItemService) {
    this.repository = repository;
    this.unidadeMedidaService = unidadeMedidaService;
    this.categoriaItemService = categoriaItemService;
  }

  public List<Item> listar() {
    return this.repository.findByAtivoTrue();
  }
  public Item porId(int id) {
    return this.repository.findById(id).orElseThrow(
        () -> new RuntimeException("Item não encontrado")
    );
  }

  @Transactional
  public Item criar(
      Item novoItem,
      int unidadeMedidaId,
      int categoriaItemId,
      int idResponsavel
  ) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    UnidadeMedida unidadeMedida = this.unidadeMedidaService.porId(unidadeMedidaId);
    CategoriaItem categoriaItem = this.categoriaItemService.porId(categoriaItemId);

    novoItem.setUnidade_medida(unidadeMedida);
    novoItem.setCategoria(categoriaItem);

    return this.repository.save(novoItem);
  }

  @Transactional
  public Item Atualizar(
      Item item,
      int id,
      int unidadeMedidaId,
      int categoriaItemId,
      int idResponsavel
  ) {
    Item itemAtual = this.porId(id);
    item.setId(itemAtual.getId());

    return this.criar(item, unidadeMedidaId, categoriaItemId, idResponsavel);
  }

  @Transactional
  public void deletar(int id, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    this.repository.updateAtivoById(false, id);
  }

  public Item itemParado(){
    return repository.findByItemParado();
  }

  public List<Item> kpiVencimento(){
    return repository.getItemVencimento();
  }

  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }
}
