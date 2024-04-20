package com.example.sustentaree.dtos.item;

import com.example.sustentaree.domain.categoria.Categoria;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class ItemDTO {

    private String nomeNota;
    private Boolean perecivel;
    private Integer diaVenciemento;
    private UnidadeMedida unidadeMedida;
    private Categoria categoria;

}
