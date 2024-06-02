package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.interacao_estoque.InteracaoEstoque;
import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueCriacaoDTO;
import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueListagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class InteracaoEstoqueMapper {

    public static final InteracaoEstoqueMapper INSTANCE = Mappers.getMapper(InteracaoEstoqueMapper.class);

    public abstract InteracaoEstoque toInteracaoEstoque(InteracaoEstoqueCriacaoDTO interacaoEstoqueCriacaoDTO);

    public abstract InteracaoEstoqueListagemDTO toInteracaoEstoqueListagemDTO(InteracaoEstoque interacaoEstoque);

    public abstract List<InteracaoEstoqueListagemDTO> toInteracaoEstoqueList(List<InteracaoEstoque> interacaoEstoqueList);

}
