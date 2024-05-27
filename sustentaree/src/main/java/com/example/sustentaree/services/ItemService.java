package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.mapper.ItemMapper;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.repositories.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;
    private final UnidadeMedidaService unidadeMedidaService;
    private final CategoriaItemService categoriaItemService;

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
