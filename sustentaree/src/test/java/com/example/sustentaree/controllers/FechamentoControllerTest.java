package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.repositories.FechamentoRepository;
import com.example.sustentaree.services.FechamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
class FechamentoControllerTest {
//    @InjectMocks
//    private FechamentoService fechamentoService;
//
//    @Mock
//    private FechamentoRepository fechamentoRepository;
//
//    @Test
//    @DisplayName("Buscar fechamento por id")
//    void deveBuscarFechamentoPorId() {
//        //GIVEN
//        Integer idBusca = 1;
//        Fechamento fechamento = new Fechamento();
//        fechamento.setId(idBusca);
//        fechamento.setDataFechamento(LocalDateTime.now());
//        fechamento.setDataInicio(LocalDateTime.now());
//        fechamento.setDataFim(LocalDateTime.now());
//        fechamento.setIsManual(1);
//
//        //WHEN
//        Mockito.when(fechamentoService.porId(idBusca)).thenReturn(fechamento);
//
//        //THEN
//        Fechamento fechamentoEncontrado = fechamentoService.porId(idBusca);
//
//        //ASSERT
//        assertEquals(fechamento, fechamentoEncontrado);
//        Mockito.verify(fechamentoRepository, Mockito.times(1)).findById(idBusca);
//
//    }

}