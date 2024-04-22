package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.dtos.item.AlterarItemDTO;
import com.example.sustentaree.dtos.item.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class ItemMapper {
    public static ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    public abstract Item toItem (ItemDTO itemDTO);
    public abstract ItemDTO toItemDTO (Item item);
    public abstract List<ItemDTO> toItemList (List<Item> itemList);
    public abstract Item toItem(AlterarItemDTO alterarItemDTO);
}
