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

    public abstract List<ProdutoListagemDTO> toProdutoList(List<Produto> produtoList);

    public abstract Produto toProduto(AlterarProdutoDTO alterarProdutoDTO);

    public static Produto toProduto(ProdutoCriacaoDTO produtoCriacaoDTO, Item item){

        Produto produto = new Produto();
        produto.setNome(produtoCriacaoDTO.getNome());
        produto.setPreco(produtoCriacaoDTO.getPreco());
        produto.setQtdProduto(produtoCriacaoDTO.getQtdProduto());
        produto.setQtdMedida(produtoCriacaoDTO.getQtdMedida());
        produto.setFkItem(item.getId());

//        Item item = new Item();
//        item.setNome(itemCriacaoDTO.getNome());
//        item.setDia_vencimento(itemCriacaoDTO.getDia_vencimento());
//        item.setPerecivel(itemCriacaoDTO.getPerecivel());
//        item.setUnidade_medida(unidadeMedida);
//        item.setCategoria(categoriaItem);

        return produto;
    }
}
