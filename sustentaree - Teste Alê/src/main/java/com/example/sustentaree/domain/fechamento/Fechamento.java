package com.example.sustentaree.domain.fechamento;

import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Fechamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDateTime dataFim;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFechamento;
    private int isManual;

//    @OneToMany
//    private InteracaoEstoque interacaoEstoque;

}
