package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.produto.Produto;
import com.example.sustentaree.dtos.produto.AlterarProdutoDTO;
import com.example.sustentaree.dtos.produto.ProdutoCriacaoDTO;
import com.example.sustentaree.dtos.produto.ProdutoListagemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class ProdutoMapper {
  public static ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);

  public abstract Produto toProduto(ProdutoCriacaoDTO produtoCriacaoDTO);

  public abstract ProdutoListagemDTO toProdutoListagemDTO(Produto produto);

  public abstract List<ProdutoListagemDTO> toProdutoListagemDTO(List<Produto> produto);

  public abstract List<ProdutoListagemDTO> toProdutoListDTO(List<Produto> produtoList);

  public abstract Produto toProdutoUpdate(AlterarProdutoDTO alterarProdutoDTO);

}
