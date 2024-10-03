package com.example.sustentaree.services;

import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.dtos.unidade_medida.UnidadeMedidaDTO;
import com.example.sustentaree.mapper.UnidadeMedidaMapper;
import com.example.sustentaree.repositories.UnidadeMedidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnidadeMedidaService {
  @Autowired
  private final UnidadeMedidaRepository repository;
  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;
  public UnidadeMedidaService(UnidadeMedidaRepository repository) {
    this.repository = repository;
  }

  public List<UnidadeMedida> listar() {
    return this.repository.findByAtivoTrue();
  }
  public UnidadeMedida porId(Integer id) {
    // TODO criar exception
    return this.repository.findById(id).orElseThrow(
        () -> new RuntimeException("Unidade de medida não encontrada"));
  }
  @Transactional
  public UnidadeMedida criar(UnidadeMedida unidadeMedida, int idResponsavel) {
    // TODO implementar regras de negócio e exceptions
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    return this.repository.save(unidadeMedida);
  }
  @Transactional
  public UnidadeMedida atualizar(UnidadeMedida unidadeMedida, int id, int idResponsavel) {
    // TODO Criar validações sobre conflitos e suas exceptions
    UnidadeMedida unidadeMedidaAtual = this.porId(id);
    unidadeMedida.setId(unidadeMedidaAtual.getId());

    return this.criar(unidadeMedida, idResponsavel);
  }
  @Transactional
  public void deletar(Integer id, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    this.repository.updateAtivoById(false, id);
  }
  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }
}
