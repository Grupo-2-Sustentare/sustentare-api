package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.unidade_medida.UnidadeMedidaDTO;
import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.util.List;

@Mapper
public abstract class UnidadeMedidaMapper {
  public static final UnidadeMedidaMapper INSTANCE =  Mappers.getMapper(UnidadeMedidaMapper.class);

  public abstract UnidadeMedida toUnidadeMedida(UnidadeMedidaDTO unidadeMedidaDTO);
  public abstract UnidadeMedidaDTO toUnidadeMedidaDTO(UnidadeMedida unidadeMedida);
  public abstract List<UnidadeMedidaDTO> toUnidadeMedidaListDTO(List<UnidadeMedida> unidadeMedidas);


}
