package com.example.sustentaree.dtos.usuario;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlterarUsuarioDTO {

    @NotBlank
    private String nome;
    @NotBlank
    private String senha;
    @NotBlank
    private String email;

}
