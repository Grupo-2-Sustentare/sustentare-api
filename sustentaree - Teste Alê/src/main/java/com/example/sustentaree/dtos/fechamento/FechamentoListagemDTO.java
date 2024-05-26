package com.example.sustentaree.dtos.fechamento;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FechamentoListagemDTO {

    private Integer id;

    private LocalDateTime dataFim;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFechamento;
    private int isManual;

//    @OneToMany
//    private InteracaoEstoque interacaoEstoque;

}
