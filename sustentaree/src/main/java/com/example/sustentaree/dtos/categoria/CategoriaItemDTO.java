package com.example.sustentaree.dtos.categoria;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoriaItemDTO {

    @NotBlank
    private String nome;

}
