package com.example.sustentaree.services;

import com.example.sustentaree.domain.grafico.ViewEntradasSaidas;
import com.example.sustentaree.domain.grafico.ViewVencerNaSemana;
import com.example.sustentaree.dtos.*;
import com.example.sustentaree.repositories.ViewEntradasSaidasRepository;
import com.example.sustentaree.repositories.ViewVencerNaSemanaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.sql.DataSource;
import java.util.ArrayList;


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

    public List<ValorEntradasSaidasMesDTO> getValorEntradasSaidasMes(LocalDate dataInicio, LocalDate dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_valor_entradas_saidas_mes(?, ?, ?, ?)}";  // Query para chamar a procedure

        List<ValorEntradasSaidasMesDTO> resultados = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.setDate(1, Date.valueOf(dataInicio));
            callableStatement.setDate(2, Date.valueOf(dataFim));
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        LocalDate data = rs.getDate("data").toLocalDate();
                        Double valorEntradas = rs.getDouble("valor_entradas");
                        Double valorSaidas = rs.getDouble("valor_saidas");

                        ValorEntradasSaidasMesDTO dto = new ValorEntradasSaidasMesDTO(data, valorEntradas, valorSaidas);
                        resultados.add(dto);
                    }
                }
            }
        }

        return resultados;
    }

    public List<PerdasPorMesDTO> getPerdasPorMes(LocalDate dataInicio, LocalDate dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_perdas_por_mes(?, ?, ?, ?)}";  // Query para chamar a procedure

        List<PerdasPorMesDTO> resultados = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.setDate(1, Date.valueOf(dataInicio));
            callableStatement.setDate(2, Date.valueOf(dataFim));
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        String tipoPerda = rs.getString("tipo_perda");
                        Integer qtdPerda = rs.getInt("qtd_perda");

                        PerdasPorMesDTO dto = new PerdasPorMesDTO(tipoPerda, qtdPerda);
                        resultados.add(dto);
                    }
                }
            }
        }

        return resultados;
    }

    public List<ComprasDTO> getComprasRegularesVsNaoPlanejadas(LocalDate dataInicio, LocalDate dataFim, String categorias, String itens) throws Exception {
        String query = "{CALL sp_compras_regulares_vs_nao_planejadas(?, ?, ?, ?)}";  // Query para chamar a procedure

        List<ComprasDTO> resultados = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.setDate(1, Date.valueOf(dataInicio));
            callableStatement.setDate(2, Date.valueOf(dataFim));
            callableStatement.setString(3, categorias);
            callableStatement.setString(4, itens);

            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        String tipoCompra = rs.getString("tipo_compra");
                        Integer qtdCompras = rs.getInt("qtd_compras");

                        ComprasDTO dto = new ComprasDTO(tipoCompra, qtdCompras);
                        resultados.add(dto);
                    }
                }
            }
        }

        return resultados;
    }
    public List<AuditoriaColaboradoresDTO> getAuditoriaColaboradores(LocalDate dataInicio, LocalDate dataFim, String responsaveis) throws Exception {
        String query = "{CALL sp_auditoria_colaboradores(?, ?, ?)}";

        List<AuditoriaColaboradoresDTO> resultados = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.setDate(1, Date.valueOf(dataInicio));
            callableStatement.setDate(2, Date.valueOf(dataFim));
            callableStatement.setString(3, responsaveis);

            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        Integer idResponsavel = rs.getInt("id_responsavel");
                        String responsavelNome = rs.getString("responsavel_nome");
                        String descricaoAuditoria = rs.getString("descricao_auditoria");
                        String tipoAudit = rs.getString("tipo_audit");
                        String detalhesRegistro = rs.getString("detalhes_registro");

                        DateTimeFormatter FORM_DATA = DateTimeFormatter.ofPattern("dd/MM");
                        LocalDate dataAcao = rs.getDate("data_acao").toLocalDate();
                        DateTimeFormatter FORM_HORA = DateTimeFormatter.ofPattern("HH:mm");
                        LocalTime horaAcao = rs.getTime("data_acao").toLocalTime();

                        String dataHoraAcao = dataAcao.format(FORM_DATA) + " " + horaAcao.format(FORM_HORA);

                        AuditoriaColaboradoresDTO dto = new AuditoriaColaboradoresDTO(
                            idResponsavel, responsavelNome, dataHoraAcao, descricaoAuditoria, tipoAudit, detalhesRegistro
                        );
                        resultados.add(dto);
                    }
                }
            }
        }

        return resultados;
    }
    public List<EntradasSaidasColaboradoresDTO> getEntradasSaidasPorColaborador(LocalDate dataInicio, LocalDate dataFim, String colaboradores) throws Exception {
        String query = "{CALL sp_entradas_saidas_por_colaborador(?, ?, ?)}";

        List<EntradasSaidasColaboradoresDTO> resultados = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             CallableStatement callableStatement = connection.prepareCall(query)) {

            callableStatement.setDate(1, Date.valueOf(dataInicio));
            callableStatement.setDate(2, Date.valueOf(dataFim));
            callableStatement.setString(3, colaboradores);

            boolean hasResults = callableStatement.execute();

            if (hasResults) {
                try (ResultSet rs = callableStatement.getResultSet()) {
                    while (rs.next()) {
                        String colaborador = rs.getString("colaborador");
                        Integer qtdEntradas = rs.getInt("qtd_entradas");
                        Integer qtdSaidas = rs.getInt("qtd_saidas");

                        EntradasSaidasColaboradoresDTO dto = new EntradasSaidasColaboradoresDTO(
                            colaborador, qtdEntradas, qtdSaidas
                        );
                        resultados.add(dto);
                    }
                }
            }
        }

        return resultados;
    }
}
