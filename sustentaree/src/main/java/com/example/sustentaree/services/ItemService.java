package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.item.ItemListagemDTO;
import com.example.sustentaree.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
  @Autowired
  private final ItemRepository repository;
  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;
  @Autowired
  private LambdaService lambdaService;
  @Autowired
  private ImagemService imagemService;
  @Autowired
  private CategoriaItemService categoriaItemService;
  @Autowired
  private UnidadeMedidaService unidadeMedidaService;

  public ItemService(ItemRepository repository) {
    this.repository = repository;
  }

  public List<ItemListagemDTO> listar() {
    List<Item> itens = this.repository.findByAtivoTrue();
    if (itens == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum item encontrado");
    }

    List<ItemListagemDTO> ItemListagemDTO = imagemService.addImagensS3Itens(itens);

    if (ItemListagemDTO == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Erro ao tentar adicionar imagem");
    }

    return ItemListagemDTO;
  }

  public List<Item> listarSemImagem() {

    return  this.repository.findByAtivoTrue();
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

  public Boolean procurarItemPorNome(String nome){
    return this.repository.existsByNome(nome).isPresent();
  }

  public Integer getUltimoId(){
    Optional<Item> item = this.repository.getUltimoId();
    if (item.isPresent()){
      return item.get().getId();
    }
    return 0;
  }
  public List<Item> listByUnidadeMedida(int id) {
    UnidadeMedida unidadeMedida = this.unidadeMedidaService.porId(id);
    return this.repository.findByUnidade_medida(unidadeMedida);
  }
  public List<Item> listByCategoriaItem (int id){
    CategoriaItem categoriaItem = this.categoriaItemService.porId(id);
    return this.repository.findByCategoria(categoriaItem);
  }
}
