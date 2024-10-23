package com.example.sustentaree.services;

import com.example.sustentaree.domain.grafico.ViewEntradasSaidas;
import com.example.sustentaree.domain.grafico.ViewVencerNaSemana;
import com.example.sustentaree.dtos.ComprasDTO;
import com.example.sustentaree.dtos.PerdasPorMesDTO;
import com.example.sustentaree.dtos.ValorEntradasSaidasMesDTO;
import com.example.sustentaree.repositories.ViewEntradasSaidasRepository;
import com.example.sustentaree.repositories.ViewVencerNaSemanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


@Service
public class GraficoService {
    private final ViewEntradasSaidasRepository viewEntradasSaidasRepository;
    private final ViewVencerNaSemanaRepository viewVencerNaSemanaRepository;

    @Autowired
    private DataSource dataSource;

    public GraficoService(ViewEntradasSaidasRepository viewEntradasSaidasRepository, ViewVencerNaSemanaRepository viewVencerNaSemanaRepository) {
        this.viewEntradasSaidasRepository = viewEntradasSaidasRepository;
        this.viewVencerNaSemanaRepository = viewVencerNaSemanaRepository;
    }

    public List<ViewEntradasSaidas> entradasSaidas() {
        List<ViewEntradasSaidas> viewEntradasSaidas = viewEntradasSaidasRepository.findAll();
        return viewEntradasSaidas;
    }

    public List<ViewVencerNaSemana> vencerNaSemana() {
        List<ViewVencerNaSemana> viewVencerNaSemanas = viewVencerNaSemanaRepository.findAll();
        return viewVencerNaSemanas;
    }

    public List<ValorEntradasSaidasMesDTO> getValorEntradasSaidasMes(Date dataInicio, Date dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_valor_entradas_saidas_mes(?, ?, ?, ?)}";  // Query para chamar a procedure

        List<ValorEntradasSaidasMesDTO> resultados = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            // Definir parâmetros de entrada
            callableStatement.setDate(1, new java.sql.Date(dataInicio.getTime()));;
            callableStatement.setDate(2, new java.sql.Date(dataFim.getTime()));
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            // Executar a procedure
            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        // Capturar os valores retornados da procedure
                        String mesAno = rs.getString("mes_ano");
                        String categoria = rs.getString("categoria");
                        String item = rs.getString("item");
                        Double valorEntradas = rs.getDouble("valor_entradas");
                        Double valorSaidas = rs.getDouble("valor_saidas");

                        // Criar o DTO e adicionar à lista de resultados
                        ValorEntradasSaidasMesDTO dto = new ValorEntradasSaidasMesDTO(mesAno, categoria, item, valorEntradas, valorSaidas);
                        resultados.add(dto);
                    }
                }
            }
        }

        return resultados;
    }

    public List<PerdasPorMesDTO> getPerdasPorMes(String dataInicio, String dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_perdas_por_mes(?, ?, ?, ?)}";  // Query para chamar a procedure

        List<PerdasPorMesDTO> resultados = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            // Definir parâmetros de entrada
            callableStatement.setString(1, dataInicio);
            callableStatement.setString(2, dataFim);
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            // Executar a procedure
            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        // Capturar os valores retornados da procedure
                        String mesAno = rs.getString("mes_ano");
                        String tipoPerda = rs.getString("tipo_perda");
                        Integer qtdPerda = rs.getInt("qtd_perda");

                        // Criar o DTO e adicionar à lista de resultados
                        PerdasPorMesDTO dto = new PerdasPorMesDTO(mesAno, tipoPerda, qtdPerda);
                        resultados.add(dto);
                    }
                }
            }
        }

        return resultados;
    }

    public List<ComprasDTO> getComprasRegularesVsNaoPlanejadas(String dataInicio, String dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_compras_regulares_vs_nao_planejadas(?, ?, ?, ?)}";  // Query para chamar a procedure

        List<ComprasDTO> resultados = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            // Definir parâmetros de entrada
            callableStatement.setString(1, dataInicio);
            callableStatement.setString(2, dataFim);
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            // Executar a procedure
            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        // Capturar os valores retornados da procedure
                        String mesAno = rs.getString("mes_ano");
                        String tipoCompra = rs.getString("tipo_compra");
                        Integer qtdCompras = rs.getInt("qtd_compras");

                        // Criar o DTO e adicionar à lista de resultados
                        ComprasDTO dto = new ComprasDTO(mesAno, tipoCompra, qtdCompras);
                        resultados.add(dto);
                    }
                }
            }
        }

        return resultados;
    }

}
