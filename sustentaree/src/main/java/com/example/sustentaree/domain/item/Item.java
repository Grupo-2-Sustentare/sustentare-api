package com.example.sustentaree.domain.item;

import com.example.sustentaree.domain.categoria.Categoria;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nomeNota;
    private Boolean perecivel;
    private Integer diaVenciemento;

    @ManyToOne
    @JoinColumn(name = "fkUnidadeMedida")
    private UnidadeMedida unidadeMedida;

    @ManyToOne
    @JoinColumn(name = "fkCategoria")
    private Categoria categoria;
}
