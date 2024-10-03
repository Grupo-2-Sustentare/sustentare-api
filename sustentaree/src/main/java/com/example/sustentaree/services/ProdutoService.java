package com.example.sustentaree.services;

import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.repositories.ProdutoRepository;
import com.example.sustentaree.services.data_structure.HashTable;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {
  @Autowired
  private final ProdutoRepository produtoRepository;

  @Autowired
  private final ItemService itemService;

  @Autowired
  private final CategoriaItemService categoriaItemService;

  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;
  public ProdutoService(
        ProdutoRepository produtoRepository,
        ItemService itemService,
        CategoriaItemService categoriaItemService
  ) {
    this.produtoRepository = produtoRepository;
    this.itemService = itemService;
    this.categoriaItemService = categoriaItemService;
  }

  public List<Produto> listar() {
    return this.produtoRepository.findByAtivoTrue();
  }

  public Produto porId(Integer id) {
    return this.produtoRepository.findById(id).orElseThrow(
          () -> new RuntimeException("Produto")
    );
  }
  @Transactional
  public Produto criar(Produto novoProduto,
                       Integer fkItem, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    novoProduto.setItem(itemService.porId(fkItem));
    return this.produtoRepository.save(novoProduto);
  }
  @Transactional
  public Produto Atualizar(
        Produto produto,
        int id,
        int itemId,
        int idResponsavel
  ) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    Produto produtoAtual = this.porId(id);
    produtoAtual.setId(produtoAtual.getId());

    return this.criar(produto, itemId, idResponsavel);
  }
  @Transactional
  public void deletar(Integer id, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    Produto produto = this.porId(id);
    this.produtoRepository.delete(produto);
  }

@Transactional
  public List<Produto> getByHash(String categoria, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    HashTable hashTable = new HashTable(this.categoriaItemService.listar());
    hashTable.insertMany(this.listar());
    return hashTable.findProdutoByCategoria(categoria);
  }

  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }
}
