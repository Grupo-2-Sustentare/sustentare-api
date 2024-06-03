package com.example.sustentaree.controllers.autenticacao.dto;

import com.example.sustentaree.domain.usuario.Usuario;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UsuarioDetalhesDto implements UserDetails {
  private String nome;

  private String senha;

  public UsuarioDetalhesDto(Usuario usuario) {
    this.nome = usuario.getNome();
    this.senha = usuario.getSenha();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities(){
    return null;
  }

  @Override
  public String getPassword() {
    return senha;
  }

  @Override
  public String getUsername() {
    return nome;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
