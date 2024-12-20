package com.example.sustentaree.services;

import com.example.sustentaree.domain.item.Item;
import com.example.sustentaree.domain.unidade_medida.UnidadeMedida;
import com.example.sustentaree.repositories.UnidadeMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnidadeMedidaService {
  @Autowired
  private final UnidadeMedidaRepository repository;
  @Autowired
  private ItemValidationService itemValidationService;
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
  public List<Item> deletar(Integer id, int idResponsavel) {
    // Define o usuário responsável na sessão
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    // Busca apenas itens ativos associados à unidade de medida
    List<Item> itens = this.itemValidationService.listByUnidadeMedidaAtivos(id, this);

    if (!itens.isEmpty()) {
      System.out.println("-> UnidadeMedida não pode ser deletada pois está associada a um ou mais itens ativos");
      return itens;
    }

    // Atualiza a unidade de medida para inativa
    this.repository.updateAtivoById(false, id);
    return itens;
  }

  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }

  public UnidadeMedida getUnidadeMedidaByNome(String nome) {
    return this.repository.findByNome(nome);
  }
}
