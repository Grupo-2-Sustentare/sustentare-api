package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.repositories.CategoriaItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoriaItemServiceTest {
  @InjectMocks
  CategoriaItemService categoriaItemService;

  @Mock
  CategoriaItemRepository categoriaItemRepository;

  @Mock
  SessaoUsuarioService sessaoUsuarioService;

  @Mock
  ItemValidationService itemValidationService;


  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    categoriaItemService.setSessaoUsuarioService(sessaoUsuarioService);
  }

  @Test
  @DisplayName("Deve listar todas as categorias de item")
  public void deveListarTodasCategoriasItem() {
    List<CategoriaItem> categoriaItems = new ArrayList<>();
    when(categoriaItemRepository.findByAtivoTrueOrderByNome()).thenReturn(categoriaItems);

    List<CategoriaItem> resultado = categoriaItemService.listar();

    assertEquals(categoriaItems, resultado);
    verify(categoriaItemRepository, times(1)).findByAtivoTrueOrderByNome();
  }

  @Test
  @DisplayName("Deve retornar categoria item por id")
  public void deveRetornarCategoriaItemPorId() {
    CategoriaItem categoriaItem = new CategoriaItem();
    categoriaItem.setId(1);

    when(categoriaItemRepository.findById(1)).thenReturn(Optional.of(categoriaItem));

    CategoriaItem resultado = categoriaItemService.porId(1);

    assertEquals(categoriaItem, resultado);
    verify(categoriaItemRepository, times(1)).findById(1);
  }

  @Test
  @DisplayName("Deve criar uma nova categoria de item")
  public void deveCriarNovaCategoriaItem() {
    CategoriaItem categoriaItem = new CategoriaItem();
    categoriaItem.setId(1);

    when(categoriaItemRepository.save(any(CategoriaItem.class))).thenReturn(categoriaItem);

    CategoriaItem resultado = categoriaItemService.criar(categoriaItem, 1);

    assertEquals(categoriaItem, resultado);
    verify(categoriaItemRepository, times(1)).save(categoriaItem);
  }

  @Test
  @DisplayName("Deve atualizar uma categoria de item")
  public void deveAtualizarCategoriaItem() {
    CategoriaItem categoriaItem = new CategoriaItem();
    categoriaItem.setId(1);

    when(categoriaItemRepository.findById(1)).thenReturn(Optional.of(categoriaItem));
    when(categoriaItemRepository.save(any(CategoriaItem.class))).thenReturn(categoriaItem);

    CategoriaItem resultado = categoriaItemService.atualizar(categoriaItem, 1, 1);

    assertEquals(categoriaItem, resultado);
    verify(categoriaItemRepository, times(1)).save(categoriaItem);
  }

  @Test
  @DisplayName("Deve lançar exceção quando a categoria de item não for encontrada")
  public void deveLancarExcecaoQuandoCategoriaItemNaoExistir() {
    int categoriaId = 999;  // ID que não existe na base de dados
    when(categoriaItemRepository.findById(categoriaId)).thenReturn(Optional.empty());

    // A exceção esperada deve ser uma RuntimeException com a mensagem que você define no serviço
    RuntimeException exception = assertThrows(RuntimeException.class, () -> {
      categoriaItemService.porId(categoriaId);
    });

    assertEquals("Categoria de item não encontrada", exception.getMessage());
    verify(categoriaItemRepository, times(1)).findById(categoriaId);
  }

//  @Test
//  public void testListByCategoriaItemCategoriaNaoAssociadaAItens() {
//    // Dados de entrada para o teste
//    int categoriaId = 1;
//    CategoriaItem categoriaItem = new CategoriaItem();
//    categoriaItem.setId(categoriaId);
//    categoriaItem.setNome("Ctegoria x");
//    categoriaItem.setAtivo(true);
//
//    // Configuração do comportamento do mock
//    when(categoriaItemService.porId(categoriaId)).thenReturn(categoriaItem); // Mock do serviço de categoria
//    when(itemValidationService.listByCategoriaItem(categoriaId, categoriaItemService)).thenReturn(Collections.emptyList()); // Nenhum item associado à categoria
//
//    // Chamada do método a ser testado
//    List<Item> resultado = itemValidationService.listByCategoriaItem(categoriaId, categoriaItemService);
//
//    // Verificação dos resultados
//    assertNotNull(resultado, "A lista não pode ser nula.");
//    assertTrue(resultado.isEmpty(), "A lista deve estar vazia, pois não há itens associados à categoria.");
//  }
//
//  @Test
//  public void testDeletarCategoriaItemAssociadaAItens() {
//    // Dados de entrada para o teste
//    Integer categoriaId = 1;
//    int idResponsavel = 123;
//
//    // Criando uma lista fictícia de itens para simular uma categoria com itens associados
//    List<Item> itens = Arrays.asList(new Item(), new Item());
//
//    // Configuração do comportamento do mock
//    when(itemValidationService.listByCategoriaItem(eq(categoriaId), any())).thenReturn(itens);
//
//    // Chamada do método a ser testado
//    List<Item> resultado = categoriaItemService.deletar(categoriaId, idResponsavel);
//
//    // Verificação dos resultados
//    assertNotNull(resultado, "A lista não pode ser nula.");
//    assertFalse(resultado.isEmpty(), "A lista não deve estar vazia, pois há itens associados.");
//
//    // Verificar que o repositório NÃO foi atualizado
//    verify(categoriaItemRepository, times(0)).updateAtivoById(false, categoriaId);
//  }
}
