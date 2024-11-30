package com.example.sustentaree.services;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemValidationService {
  @Autowired
  private ItemRepository itemRepository;

  public List<Item> listByUnidadeMedidaAtivos(int id, UnidadeMedidaService unidadeMedidaService) {
    UnidadeMedida unidadeMedida = unidadeMedidaService.porId(id);
//    return itemRepository.findByUnidade_medida(unidadeMedida);
    // Busca apenas itens ativos associados à unidade de medida pelo ID
    return itemRepository.findActiveItemsByUnidade_medida(unidadeMedida);
  }

  public List<Item> listByCategoriaItemAtivos(int id, CategoriaItemService categoriaItemService) {
//    CategoriaItem categoriaItem = categoriaItemService.porId(id);
//    return itemRepository.findByCategoria(categoriaItem);
    // Usa o ID diretamente para buscar itens ativos
    return itemRepository.findActiveItemsByCategoriaId(id);
  }
}