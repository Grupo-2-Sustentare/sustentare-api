package com.example.sustentaree.dtos;

public class AuditoriaColaboradoresDTO {
  private Integer idResponsavel;
  private String responsavelNome;
  private String dataAcao;
  private String descricaoAuditoria;
  private String tipoAudit;
  private String detalhesRegistro;

  public AuditoriaColaboradoresDTO(Integer idResponsavel, String responsavelNome, String dataAcao, String descricaoAuditoria, String tipoAudit, String detalhesRegistro) {
    this.idResponsavel = idResponsavel;
    this.responsavelNome = responsavelNome;
    this.dataAcao = dataAcao;
    this.descricaoAuditoria = descricaoAuditoria;
    this.tipoAudit = tipoAudit;
    this.detalhesRegistro = detalhesRegistro;
  }

  public Integer getIdResponsavel() {
    return idResponsavel;
  }

  public void setIdResponsavel(Integer idResponsavel) {
    this.idResponsavel = idResponsavel;
  }

  public String getResponsavelNome() {
    return responsavelNome;
  }

  public void setResponsavelNome(String responsavelNome) {
    this.responsavelNome = responsavelNome;
  }

  public String getDataAcao() {
    return dataAcao;
  }

  public void setDataAcao(String dataAcao) {
    this.dataAcao = dataAcao;
  }

  public String getDescricaoAuditoria() {
    return descricaoAuditoria;
  }

  public void setDescricaoAuditoria(String descricaoAuditoria) {
    this.descricaoAuditoria = descricaoAuditoria;
  }

  public String getTipoAudit() {
    return tipoAudit;
  }

  public void setTipoAudit(String tipoAudit) {
    this.tipoAudit = tipoAudit;
  }

  public String getDetalhesRegistro() {
    return detalhesRegistro;
  }

  public void setDetalhesRegistro(String detalhesRegistro) {
    this.detalhesRegistro = detalhesRegistro;
  }
}
