package com.example.sustentaree.dtos.categoria;


import io.swagger.v3.oas.annotations.media.Schema;

public class CategoriaItemDTO {
@Schema(description = "Nome da categoria", example = "Alimentos")
    private String nome;






    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
