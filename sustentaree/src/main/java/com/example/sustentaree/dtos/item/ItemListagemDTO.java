package com.example.sustentaree.dtos.item;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemListagemDTO {

    private String nome;
    private Boolean perecivel;
    private Integer dia_vencimento;
    private UnidadeMedida unidade_medida;
    private CategoriaItem categoria;

}
