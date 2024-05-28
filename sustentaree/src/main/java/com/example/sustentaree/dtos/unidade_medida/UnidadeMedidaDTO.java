package com.example.sustentaree.dtos.unidade_medida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnidadeMedidaDTO {

    @NotBlank
    private String categoria;
    @NotNull
    private Double conversaoPadrao;

}
