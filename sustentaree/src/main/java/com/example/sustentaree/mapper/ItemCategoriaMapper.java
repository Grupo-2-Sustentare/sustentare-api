package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.dtos.categoria.CategoriaItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class ItemCategoriaMapper {
    public static final ItemCategoriaMapper INSTANCE = Mappers.getMapper(ItemCategoriaMapper.class);

    public abstract CategoriaItem toCategoriaItem (CategoriaItemDTO categoriaItemDTO);
    public abstract CategoriaItemDTO toCategoriaItemDTO(CategoriaItem categoriaItem);
    public abstract List<CategoriaItemDTO> toCategoriaItemListDTO(List<CategoriaItem> categoriaItems);
}
