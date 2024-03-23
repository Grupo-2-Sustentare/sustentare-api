package school.sptech.susutentarecrud.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import school.sptech.susutentarecrud.domain.produto.Produto;
import school.sptech.susutentarecrud.domain.produto.ProdutoDTO;

@Mapper
public abstract class ProdutoMapper {

    public static ProdutoMapper INSTANCE = Mappers.getMapper(ProdutoMapper.class);
    public abstract Produto toProduto(ProdutoDTO produtoDTO);


      /*

    ---------------------
     MAPPER FEITO NA MAO
    ---------------------

    List<Produto> produtos = new ArrayList<>();

    public Produto toProduto(ProdutoDTO dados){
        Produto produto = new Produto();
        produto.set(dados.getNome());

        ...

        produtos.add(produto)

        return produto;

    }
     */


}
