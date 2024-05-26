package com.example.sustentaree.dtos.produto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ProdutoCriacaoDTO {

    // @ManyToOne -> trazer o id do Item correspondente ao produto
    private Integer fkItem;

    @NotBlank
    private String nome;
    @NotNull
    @Positive
    private Double preco;
    @NotNull
    @Positive
    private Integer qtdProduto;
    @NotNull
    @Positive
    private Double qtdMedida;
}
