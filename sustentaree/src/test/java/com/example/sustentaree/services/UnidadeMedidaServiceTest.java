package com.example.sustentaree.services;

import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.repositories.UnidadeMedidaRepository;
import com.example.sustentaree.services.SessaoUsuarioService;
import com.example.sustentaree.services.UnidadeMedidaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UnidadeMedidaServiceTest {

  @InjectMocks
  private UnidadeMedidaService unidadeMedidaService;

  @Mock
  private UnidadeMedidaRepository unidadeMedidaRepository;

  @Mock
  private SessaoUsuarioService sessaoUsuarioService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    unidadeMedidaService.setSessaoUsuarioService(sessaoUsuarioService);
  }

  @Test
  @DisplayName("Deve listar todas as unidades de medida")
  public void testListar() {
    UnidadeMedida unidadeMedida = new UnidadeMedida();
    unidadeMedida.setId(1);

    when(unidadeMedidaRepository.findAll()).thenReturn(java.util.List.of(unidadeMedida));

    var result = unidadeMedidaService.listar();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals(1, result.get(0).getId());
  }

  @Test
  @DisplayName("Deve retornar unidade de medida por id")
  public void testPorId() {
    UnidadeMedida unidadeMedida = new UnidadeMedida();
    unidadeMedida.setId(1);

    when(unidadeMedidaRepository.findById(1)).thenReturn(Optional.of(unidadeMedida));

    UnidadeMedida result = unidadeMedidaService.porId(1);

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  @DisplayName("Deve criar uma nova unidade de medida")
  public void testCriar() {
    UnidadeMedida unidadeMedida = new UnidadeMedida();
    unidadeMedida.setId(1);

    when(unidadeMedidaRepository.save(unidadeMedida)).thenReturn(unidadeMedida);

    UnidadeMedida result = unidadeMedidaService.criar(unidadeMedida, 1);

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  @DisplayName("Deve atualizar unidade de medida")
  public void testAtualizar() {
    UnidadeMedida unidadeMedida = new UnidadeMedida();
    unidadeMedida.setId(1);

    when(unidadeMedidaRepository.findById(1)).thenReturn(Optional.of(unidadeMedida));
    when(unidadeMedidaRepository.save(unidadeMedida)).thenReturn(unidadeMedida);

    UnidadeMedida result = unidadeMedidaService.atualizar(unidadeMedida, 1, 1);

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  @DisplayName("Deve deletar unidade de medida")
  public void testDeletar() {
    UnidadeMedida unidadeMedida = new UnidadeMedida();
    unidadeMedida.setId(1);

    when(unidadeMedidaRepository.findById(1)).thenReturn(Optional.of(unidadeMedida));
    doNothing().when(unidadeMedidaRepository).delete(unidadeMedida);

    assertDoesNotThrow(() -> unidadeMedidaService.deletar(1, 1));
  }
}