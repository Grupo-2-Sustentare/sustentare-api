package com.example.sustentaree.dtos.usuario;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class AlterarUsuarioDTO {
    private String senha;
    private Integer acesso;
}
