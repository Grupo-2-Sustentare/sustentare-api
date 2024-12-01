package com.example.sustentaree.services;

import com.example.sustentaree.domain.grafico.ViewItemParado;
import com.example.sustentaree.dtos.KpiComprasNaoPlanejadasDTO;
import com.example.sustentaree.dtos.KpiPerdasDto;
import com.example.sustentaree.dtos.KpiValorTotalEntradasDTO;
import com.example.sustentaree.dtos.KpiValorTotalSaidasDTO;
import com.example.sustentaree.repositories.ViewItemParadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

@Service
public class KpisService {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ViewItemParadoRepository itemParadoRepository;

    @Autowired
    private DataSource dataSource;


    public List<ViewItemParado> itemParado() {
        List<ViewItemParado> itens = itemParadoRepository.findAll();
        return itens;
    }
    public KpiPerdasDto getKpiPerdas(LocalDate dataInicio, LocalDate dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_kpi_perdas(?, ?, ?, ?, ?, ?)}";  // Query para chamar a procedure

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            // Definir parâmetros de entrada
            callableStatement.setDate(1, java.sql.Date.valueOf(dataInicio));
            callableStatement.setDate(2, java.sql.Date.valueOf(dataFim));
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            // Definir parâmetros de saída
            callableStatement.registerOutParameter(5, Types.INTEGER);  // Para total_perdas
            callableStatement.registerOutParameter(6, Types.VARCHAR);  // Para situacao

            // Executar a procedure
            callableStatement.execute();

            // Capturar os valores de saída
            int totalPerdas = callableStatement.getInt(5);
            String situacao = callableStatement.getString(6);

            // Retornar os valores em um DTO
          return new KpiPerdasDto(totalPerdas, situacao);
        }
    }

    public KpiComprasNaoPlanejadasDTO getKpiComprasNaoPlanejadas(LocalDate dataInicio, LocalDate dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_kpi_compras_nao_planejadas(?, ?, ?, ?, ?, ?)}";  // Query para chamar a procedure

        KpiComprasNaoPlanejadasDTO resultado = null;

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            // Definir parâmetros de entrada
            callableStatement.setDate(1, java.sql.Date.valueOf(dataInicio));
            callableStatement.setDate(2, java.sql.Date.valueOf(dataFim));
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            // Registrar parâmetros de saída
            callableStatement.registerOutParameter(5, java.sql.Types.INTEGER); // total_compras_nao_planejadas
            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR); // situacao

            // Executar a procedure
            callableStatement.execute();

            // Capturar os valores de saída
            Integer totalComprasNaoPlanejadas = callableStatement.getInt(5);
            String situacao = callableStatement.getString(6);

            // Criar o DTO com os resultados
            resultado = new KpiComprasNaoPlanejadasDTO(totalComprasNaoPlanejadas, situacao);
        }

        return resultado;
    }

    public KpiValorTotalEntradasDTO getKpiValorTotalEntradas(LocalDate dataInicio, LocalDate dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_kpi_valor_total_entradas(?, ?, ?, ?, ?)}";  // Query para chamar a procedure

        KpiValorTotalEntradasDTO resultado = null;

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            // Definir parâmetros de entrada
            callableStatement.setDate(1, java.sql.Date.valueOf(dataInicio));
            callableStatement.setDate(2, java.sql.Date.valueOf(dataFim));
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            // Registrar parâmetro de saída
            callableStatement.registerOutParameter(5, java.sql.Types.DECIMAL); // total_entradas

            // Executar a procedure
            callableStatement.execute();

            // Capturar o valor de saída
            BigDecimal totalEntradas = callableStatement.getBigDecimal(5);

            // Criar o DTO com os resultados
            resultado = new KpiValorTotalEntradasDTO(totalEntradas);
        }

        return resultado;
    }

    public KpiValorTotalSaidasDTO getKpiValorTotalSaidas(LocalDate dataInicio, LocalDate dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_kpi_valor_total_saidas(?, ?, ?, ?, ?)}";  // Query para chamar a procedure

        KpiValorTotalSaidasDTO resultado = null;

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            // Definir parâmetros de entrada
            callableStatement.setDate(1, java.sql.Date.valueOf(dataInicio));
            callableStatement.setDate(2, java.sql.Date.valueOf(dataFim));
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            // Registrar parâmetro de saída
            callableStatement.registerOutParameter(5, java.sql.Types.DECIMAL); // total_saidas

            // Executar a procedure
            callableStatement.execute();

            // Capturar o valor de saída
            BigDecimal totalSaidas = callableStatement.getBigDecimal(5);

            // Criar o DTO com os resultados
            resultado = new KpiValorTotalSaidasDTO(totalSaidas);
        }

        return resultado;
    }

}
