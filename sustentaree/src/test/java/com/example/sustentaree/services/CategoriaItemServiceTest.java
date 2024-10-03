package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.repositories.CategoriaItemRepository;
import com.example.sustentaree.services.CategoriaItemService;
import com.example.sustentaree.services.SessaoUsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CategoriaItemServiceTest {
  @InjectMocks
  CategoriaItemService categoriaItemService;

  @Mock
  CategoriaItemRepository categoriaItemRepository;

  @Mock
  SessaoUsuarioService sessaoUsuarioService;

  @BeforeEach
  public void init() {
    MockitoAnnotations.openMocks(this);
    categoriaItemService.setSessaoUsuarioService(sessaoUsuarioService);
  }
  @Test
  @DisplayName("Deve listar todas as categorias de item")
  public void deveListarTodasCategoriasItem() {
    List<CategoriaItem> categoriaItems = new ArrayList<>();
    when(categoriaItemRepository.findByAtivoTrue()).thenReturn(categoriaItems);

    List<CategoriaItem> resultado = categoriaItemService.listar();

    assertEquals(categoriaItems, resultado);
    verify(categoriaItemRepository, times(1)).findByAtivoTrue();
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
  @DisplayName("Deve deletar uma categoria de item")
  public void deveDeletarCategoriaItem() {
    CategoriaItem categoriaItem = new CategoriaItem();
    categoriaItem.setId(1);

    when(categoriaItemRepository.findById(1)).thenReturn(Optional.of(categoriaItem));
    doNothing().when(categoriaItemRepository).delete(categoriaItem);

    categoriaItemService.deletar(1, 1);

    verify(categoriaItemRepository, times(1)).delete(categoriaItem);
  }

}
