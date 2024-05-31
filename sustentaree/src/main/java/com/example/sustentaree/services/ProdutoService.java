package com.example.sustentaree.services;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.dtos.produto.ProdutoCriacaoDTO;
import com.example.sustentaree.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
    @Autowired
    private final ProdutoRepository produtoRepository;

    @Autowired
    private final ItemService itemService;

    public ProdutoService(ProdutoRepository produtoRepository, ItemService itemService) {
        this.produtoRepository = produtoRepository;
        this.itemService = itemService;
    }

    public List<Produto> listar() {
        return this.produtoRepository.findAll();
    }
    public Produto porId(Integer id) {
        return this.produtoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Produto")
        );
    }

    public Produto criar(Produto novoProduto, Integer fkItem) {
        novoProduto.setItem(itemService.porId(fkItem));
        return this.produtoRepository.save(novoProduto);
    }

    public Produto Atualizar(
            Produto produto,
            int id,
            int itemId
    ) {
        Produto produtoAtual = this.porId(id);
        produtoAtual.setId(produtoAtual.getId());

        return this.criar(produto, itemId);
    }

    public void deletar(Integer id) {
        Produto produto = this.porId(id);
        this.produtoRepository.delete(produto);
    }

}
