package com.example.sustentaree.dtos.interacaoEstoque;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class InteracaoEstoqueListagemDTO {
    private Integer id;

    //    @ManyToOne -> trazer o id do Produto correspondente até interação
    private Integer fkProduto;

    private LocalDateTime dataHora;

    //    0 ou 1 fechamento estoque para cada interação do estoque
    private Integer fkFechamentoEstoque;

    private String categoriaInteracao;
}
