package com.example.sustentaree.dtos.produto;

import lombok.Data;

@Data
public class ProdutoListagemDTO {

    private Integer id;

    // @ManyToOne -> trazer o id do Item correspondente ao produto
    private Integer fkItem;

    private String nome;
    private Double preco;
    private Integer qtdProduto;
    private Double qtdMedida;
}
