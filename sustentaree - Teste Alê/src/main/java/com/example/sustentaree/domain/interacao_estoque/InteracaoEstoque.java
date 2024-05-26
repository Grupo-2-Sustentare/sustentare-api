package com.example.sustentaree.domain.interacao_estoque;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class InteracaoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

//    @ManyToOne -> trazer o id do Produto correspondente até interação
    private Integer fkProduto;

    private LocalDateTime dataHora;

//    0 ou 1 fechamento estoque para cada interação do estoque
    private Integer fkFechamentoEstoque;

    private String categoriaInteracao;
}
