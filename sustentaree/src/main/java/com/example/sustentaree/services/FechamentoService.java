package com.example.sustentaree.services;

import com.example.sustentaree.domain.categoria.CategoriaItem;
import com.example.sustentaree.domain.fechamento.Fechamento;
import com.example.sustentaree.exception.EntidadeNaoEncontradaException;
import com.example.sustentaree.repositories.FechamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FechamentoService {
  @Autowired
  private final FechamentoRepository repository;
  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;
  public FechamentoService(FechamentoRepository repository) {
    this.repository = repository;
  }

  public List<Fechamento> listar() {
    return this.repository.findAll();
  }

  public Fechamento porId(Integer id) {
    Optional<Fechamento> fechamentoOpt =
        repository.findById(id);

    if (fechamentoOpt.isEmpty()) {
      throw new EntidadeNaoEncontradaException("Fechamento");
    }

    return fechamentoOpt.get();
  }

  @Transactional
  public Fechamento criar(Fechamento fechamento, int idResponsavel) {
    if (fechamento == null){
      throw new IllegalArgumentException("Não foi passado um Fechamento");
    }
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    return repository.save(fechamento);
  }

  @Transactional
  public Fechamento atualizar(Fechamento fechamento, int id, int idResponsavel) {
    //    TODO Exception de conflito
    Fechamento fechamentoAtual = this.porId(id);
    fechamento.setId(fechamentoAtual.getId());

    return this.criar(fechamento, idResponsavel);
  }
//  @Transactional
//  public void deletar(Integer id, int idResponsavel) {
//    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
//
//    Fechamento fechamento = this.porId(id);
//    this.repository.delete(fechamento);
//  }

  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }
}
