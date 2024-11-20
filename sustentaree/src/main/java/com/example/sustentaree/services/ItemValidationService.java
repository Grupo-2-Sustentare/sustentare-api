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

  public List<Item> listByUnidadeMedida(int id, UnidadeMedidaService unidadeMedidaService) {
    UnidadeMedida unidadeMedida = unidadeMedidaService.porId(id);
    return itemRepository.findByUnidade_medida(unidadeMedida);
  }

  public List<Item> listByCategoriaItem(int id, CategoriaItemService categoriaItemService) {
    CategoriaItem categoriaItem = categoriaItemService.porId(id);
    return itemRepository.findByCategoria(categoriaItem);
  }
}