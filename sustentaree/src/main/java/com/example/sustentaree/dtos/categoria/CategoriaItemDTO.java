package com.example.sustentaree.dtos.categoria;


import jakarta.validation.constraints.NotBlank;

public class CategoriaItemDTO {

    @NotBlank
    private String nome;






    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
