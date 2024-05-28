package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.mapper.ItemMapper;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    @Autowired
    private final ItemRepository repository;
    @Autowired
    private final UnidadeMedidaService unidadeMedidaService;
    @Autowired
    private final CategoriaItemService categoriaItemService;

    public ItemService(ItemRepository repository, UnidadeMedidaService unidadeMedidaService, CategoriaItemService categoriaItemService) {
        this.repository = repository;
        this.unidadeMedidaService = unidadeMedidaService;
        this.categoriaItemService = categoriaItemService;
    }

    public List<Item> listar() {
        return this.repository.findAll();
    }
    public Item porId(Integer id) {
        return this.repository.findById(id).orElseThrow(
              () -> new RuntimeException("Item")
        );
    }

    public Item criar(
          Item novoItem,
          int unidadeMedidaId,
          int categoriaItemId
    ) {
        UnidadeMedida unidadeMedida = this.unidadeMedidaService.porId(unidadeMedidaId);
        CategoriaItem categoriaItem = this.categoriaItemService.porId(categoriaItemId);
        novoItem.setUnidade_medida(unidadeMedida);
        novoItem.setCategoria(categoriaItem);

        return this.repository.save(novoItem);
    }

    public Item Atualizar(
          Item item,
          int id,
          int unidadeMedidaId,
          int categoriaItemId
    ) {
        Item itemAtual = this.porId(id);
        item.setId(itemAtual.getId());

        return this.criar(item, unidadeMedidaId, categoriaItemId);
    }

    public void deletar(Integer id) {
        Item item = this.porId(id);
        this.repository.delete(item);
    }
}
