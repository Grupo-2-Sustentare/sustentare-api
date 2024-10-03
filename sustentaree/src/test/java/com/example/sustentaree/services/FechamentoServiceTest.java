package com.example.sustentaree.services;

import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.repositories.FechamentoRepository;
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

class FechamentoServiceTest {

    @InjectMocks
    private FechamentoService fechamentoService;

    @Mock
    private FechamentoRepository fechamentoRepository;

    @Mock
    private SessaoUsuarioService sessaoUsuarioService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        fechamentoService.setSessaoUsuarioService(sessaoUsuarioService);
    }

    @Test
    @DisplayName("Deve listar todos os Fechamentos")
    public void listarFechamento() {
        List<Fechamento> fechamentos = new ArrayList<>();
        when(fechamentoRepository.findByAtivoTrue()).thenReturn(fechamentos);

        List<Fechamento> result = fechamentoService.listar();

        assertEquals(fechamentos, result);
        verify(fechamentoRepository, times(1)).findByAtivoTrue();
    }

    @Test
    @DisplayName("Deve buscar um fechamento por id")
    public void buscarFechamentoPorId() {
        Fechamento fechamento = new Fechamento();
        fechamento.setId(1);

        when(fechamentoRepository.findById(1)).thenReturn(Optional.of(fechamento));

        Fechamento result = fechamentoService.porId(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

    @Test
    @DisplayName("Deve criar um fechamento")
    public void criarFechamento() {
        Fechamento fechamento = new Fechamento();
        fechamento.setId(1);
        fechamento.setIsManual(1);

        when(fechamentoRepository.save(fechamento)).thenReturn(fechamento);

        Fechamento result = fechamentoService.criar(fechamento, 100);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(1, result.getIsManual());
    }

    @Test
    @DisplayName("Deve validar se o fechamento que foi passado está null")
    public void criarFechamentoComNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            fechamentoService.criar(null, 100);
        });

        assertEquals("Não foi passado um Fechamento", exception.getMessage());
    }

    @Test
    @DisplayName("Deve Atualizar um fechamento")
    public void atualizarFechamento() {
        Fechamento fechamento = new Fechamento();
        fechamento.setId(1);

        when(fechamentoRepository.findById(1)).thenReturn(Optional.of(fechamento));
        when(fechamentoRepository.save(fechamento)).thenReturn(fechamento);

        Fechamento result = fechamentoService.atualizar(fechamento, 1, 100);

        assertNotNull(result);
        assertEquals(1, result.getId());
    }

}