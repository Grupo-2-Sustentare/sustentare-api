package com.example.sustentaree.dtos.fechamento;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FechamentoCriacaoDTO {

    @NotNull
    @Future
    private LocalDateTime dataFim;
    @NotNull
    @PastOrPresent
    private LocalDateTime dataInicio;

    @NotNull
    private LocalDateTime dataFechamento;
    @NotNull
    private int isManual;

//    @OneToMany
//    private InteracaoEstoque interacaoEstoque;
}
