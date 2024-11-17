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
  private SessaoUsuarioService sessaoUsuarioService;
  @Autowired
  private ItemService itemService;
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
    List<Item> item = this.itemService.listByUnidadeMedida(id);
    if (item.size() > 0) {
      throw new RuntimeException("Unidade de medida não pode ser deletada pois está associada a um item");
    }
    this.repository.updateAtivoById(false, id);
  }
  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }

  public UnidadeMedida getUnidadeMedidaByNome(String nome) {
    return this.repository.findByNome(nome);
  }
}
