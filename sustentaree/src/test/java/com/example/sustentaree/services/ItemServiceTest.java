package com.example.sustentaree.services;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.repositories.ItemRepository;
import com.example.sustentaree.services.CategoriaItemService;
import com.example.sustentaree.services.ItemService;
import com.example.sustentaree.services.SessaoUsuarioService;
import com.example.sustentaree.services.UnidadeMedidaService;
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

public class ItemServiceTest {

  @InjectMocks
  private ItemService itemService;

  @Mock
  private ItemRepository itemRepository;

  @Mock
  private UnidadeMedidaService unidadeMedidaService;

  @Mock
  private CategoriaItemService categoriaItemService;

  @Mock
  private SessaoUsuarioService sessaoUsuarioService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    itemService.setSessaoUsuarioService(sessaoUsuarioService);
  }

  @Test
  @DisplayName("Deve listar todos os itens")
  public void testListar() {
    List<Item> items = new ArrayList<>();
    when(itemRepository.findByAtivoTrue()).thenReturn(items);

    List<Item> result = itemService.listar();

    assertEquals(items, result);
    verify(itemRepository, times(1)).findByAtivoTrue();
  }

  @Test
  @DisplayName("Deve retornar item por id")
  public void testPorId() {
    Item item = new Item();
    item.setId(1);

    when(itemRepository.findById(1)).thenReturn(Optional.of(item));

    Item result = itemService.porId(1);

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  @DisplayName("Deve criar um novo item")
  public void testCriar() {
    Item item = new Item();
    item.setId(1);

    when(itemRepository.save(item)).thenReturn(item);

    Item result = itemService.criar(item, 1, 1, 1);

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  @DisplayName("Deve atualizar um item")
  public void testAtualizar() {
    Item item = new Item();
    item.setId(1);

    when(itemRepository.findById(1)).thenReturn(Optional.of(item));
    when(itemRepository.save(item)).thenReturn(item);

    Item result = itemService.Atualizar(item, 1, 1, 1, 1);

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  @DisplayName("Deve deletar um item")
  public void testDeletar() {
    Item item = new Item();
    item.setId(1);

    when(itemRepository.findById(1)).thenReturn(Optional.of(item));
    doNothing().when(itemRepository).delete(item);

    assertDoesNotThrow(() -> itemService.deletar(1, 1));
  }
  @Test
  @DisplayName("Deve retornar item parado")
  public void testItemParado() {
    Item item = new Item();
    item.setId(1);

    when(itemRepository.findByItemParado()).thenReturn(item);

    Item result = itemService.itemParado();

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  @DisplayName("Deve retornar kpi vencimento")
  public void testKpiVencimento() {
    List<Item> items = new ArrayList<>();
    items.add(new Item());

    when(itemRepository.getItemVencimento()).thenReturn(items);

    List<Item> result = itemService.kpiVencimento();

    assertNotNull(result);
    assertEquals(1, result.size());
  }
}