package com.example.sustentaree.dtos.categoria;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class CategoriaDTO {

    private String nomeCategoria;






    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
}
