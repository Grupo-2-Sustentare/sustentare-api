package school.sptech.susutentarecrud.domain.produto;

import org.antlr.v4.runtime.misc.NotNull;

public record ProdutoDTO(

        String empresa,
        String lote,
        String nome,
        Double preco,
        Integer qtdProduto



) {
}
