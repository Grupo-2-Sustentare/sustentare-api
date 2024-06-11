package com.example.sustentaree.services;

import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.dtos.produto.ProdutoCriacaoDTO;
import com.example.sustentaree.mapper.ProdutoMapper;
import com.example.sustentaree.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProdutoServiceTest {

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private SessaoUsuarioService sessaoUsuarioService;

    @Mock
    private ItemService itemService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        produtoService.setSessaoUsuarioService(sessaoUsuarioService);
    }

    @Test
    @DisplayName("Deve listar todos os Produtos")
    public void listarProdutos() {
        List<Produto> produtos = new ArrayList<>();
        when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> result = produtoService.listar();

        assertEquals(produtos, result);
        verify(produtoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar Produto por id")
    public void produtoPorId() {
        Produto produto = new Produto();
        produto.setId(1);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));

        Produto result = produtoService.porId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Deve criar um produto")
    public void criarUmProduto() {
        Integer fkItem = 1;
        int idResponsavel = 100;
        Produto produto = new Produto();
        produto.setNome("Camil");
        produto.setQtdProduto(3);
        produto.setPreco(3.50);
        produto.setQtdMedida(3.0);
        produto.setItem(itemService.porId(fkItem));

        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto result = produtoService.criar(produto, fkItem, idResponsavel);

        assertNotNull(result);
        assertEquals(3, result.getQtdProduto());
        assertEquals(3.50, result.getPreco());
        assertEquals(3.0, result.getQtdMedida());

    }

    @Test
    @DisplayName("Deve atualizar um produto")
    public void atualizar() {
        Produto produto = new Produto();
        produto.setId(1);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        when(produtoRepository.save(produto)).thenReturn(produto);

        Produto result = produtoService.Atualizar(produto, 1, 1, 100);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Deve deletar um produto")
    public void deletarProduto() {
        Produto produto = new Produto();
        produto.setId(1);

        when(produtoRepository.findById(1)).thenReturn(Optional.of(produto));
        doNothing().when(produtoRepository).delete(produto);

        assertDoesNotThrow(() -> produtoService.deletar(1, 100));
    }
}