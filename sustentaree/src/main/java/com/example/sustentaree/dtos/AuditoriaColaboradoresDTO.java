package com.example.sustentaree.dtos;

import java.util.Date;

public class AuditoriaColaboradoresDTO {
  private Integer idResponsavel;
  private String responsavelNome;
  private Date dataAcao;
  private String descricaoAuditoria;
  private String tipoAudit;
  private String detalhesRegistro;

  public AuditoriaColaboradoresDTO(Integer idResponsavel, String responsavelNome, Date dataAcao, String descricaoAuditoria, String tipoAudit, String detalhesRegistro) {
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

  public Date getDataAcao() {
    return dataAcao;
  }

  public void setDataAcao(Date dataAcao) {
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
