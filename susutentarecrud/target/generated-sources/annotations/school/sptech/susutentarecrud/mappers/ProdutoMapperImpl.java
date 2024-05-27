package school.sptech.susutentarecrud.mappers;

import javax.annotation.processing.Generated;
import school.sptech.susutentarecrud.domain.produto.Produto;
import school.sptech.susutentarecrud.domain.produto.ProdutoDTO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-03-26T18:48:54-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
public class ProdutoMapperImpl extends ProdutoMapper {

    @Override
    public Produto toProduto(ProdutoDTO produtoDTO) {
        if ( produtoDTO == null ) {
            return null;
        }

        Produto produto = new Produto();

        produto.setEmpresa( produtoDTO.empresa() );
        produto.setLote( produtoDTO.lote() );
        produto.setNome( produtoDTO.nome() );
        produto.setPreco( produtoDTO.preco() );
        produto.setQtdProduto( produtoDTO.qtdProduto() );

        return produto;
    }
}
