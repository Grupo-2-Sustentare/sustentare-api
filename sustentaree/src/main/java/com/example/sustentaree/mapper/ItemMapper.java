package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.item.AlterarItemDTO;
import com.example.sustentaree.dtos.item.ItemCriacaoDTO;
import com.example.sustentaree.dtos.item.ItemListagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class ItemMapper {
  public static ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

  public abstract Item toItem (ItemCriacaoDTO itemCriacaoDTO);
  public abstract ItemListagemDTO toItemListagemDTO (Item item);
  public abstract List<ItemListagemDTO> toItemListDto (List<Item> itemList);
  public abstract Item toItemUpdate(AlterarItemDTO alterarItemDTO);

}
