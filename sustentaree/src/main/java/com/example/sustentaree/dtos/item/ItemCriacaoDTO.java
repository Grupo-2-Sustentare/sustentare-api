package com.example.sustentaree.dtos.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCriacaoDTO {
    @NotBlank
    private String nome;
    @NotNull
    private Boolean perecivel;
    private Integer dia_vencimento;

}
