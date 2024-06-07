package com.example.sustentaree.domain.interacao_estoque;

import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.domain.produto.Produto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class InteracaoEstoque {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_interacao_estoque")
  private Integer id;

  @ManyToOne
  @JoinColumn(name = "fk_produto")
  private Produto produto;

  @Column(name = "data_hora")
  private LocalDateTime dataHora;

  @ManyToOne
  @JoinColumn(name = "fk_fechamento_estoque")
  private Fechamento fechamentoEstoque;

  @Column(name = "categoria_interacao")
  private String categoriaInteracao;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public LocalDateTime getDataHora() {
    return dataHora;
  }

  public void setDataHora(LocalDateTime dataHora) {
    this.dataHora = dataHora;
  }

  public Produto getProduto() {
    return produto;
  }

  public void setProduto(Produto produto) {
    this.produto = produto;
  }

  public Fechamento getFechamentoEstoque() {
    return fechamentoEstoque;
  }

  public void setFechamentoEstoque(Fechamento fechamentoEstoque) {
    this.fechamentoEstoque = fechamentoEstoque;
  }

  public String getCategoriaInteracao() {
    return categoriaInteracao;
  }

  public void setCategoriaInteracao(String categoriaInteracao) {
    this.categoriaInteracao = categoriaInteracao;
  }
}
