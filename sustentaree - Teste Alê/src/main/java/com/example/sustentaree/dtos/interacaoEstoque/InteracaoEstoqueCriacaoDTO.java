package com.example.sustentaree.dtos.interacaoEstoque;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class InteracaoEstoqueCriacaoDTO {

    @NotNull
    //    @ManyToOne -> trazer o id do Produto correspondente até interação
    private Integer fkProduto;

    @NotNull
    private LocalDateTime dataHora;

    //    0 ou 1 fechamento estoque para cada interação do estoque
    private Integer fkFechamentoEstoque;

    @NotBlank
    private String categoriaInteracao;
}
