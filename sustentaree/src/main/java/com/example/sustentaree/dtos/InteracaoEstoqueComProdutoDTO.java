package com.example.sustentaree.dtos;

import com.example.sustentaree.dtos.interacaoEstoque.InteracaoEstoqueCriacaoDTO;
import com.example.sustentaree.dtos.produto.ProdutoCriacaoDTO;
import jakarta.validation.Valid;

public class InteracaoEstoqueComProdutoDTO {
  @Valid
  private InteracaoEstoqueCriacaoDTO interacaoEstoqueCriacaoDTO;

  @Valid
  private ProdutoCriacaoDTO produtoCriacaoDTO;

  public InteracaoEstoqueCriacaoDTO getInteracaoEstoqueCriacaoDTO() {
    return interacaoEstoqueCriacaoDTO;
  }

  public void setInteracaoEstoqueCriacaoDTO(InteracaoEstoqueCriacaoDTO interacaoEstoqueCriacaoDTO) {
    this.interacaoEstoqueCriacaoDTO = interacaoEstoqueCriacaoDTO;
  }

  public ProdutoCriacaoDTO getProdutoCriacaoDTO() {
    return produtoCriacaoDTO;
  }

  public void setProdutoCriacaoDTO(ProdutoCriacaoDTO produtoCriacaoDTO) {
    this.produtoCriacaoDTO = produtoCriacaoDTO;
  }
}
