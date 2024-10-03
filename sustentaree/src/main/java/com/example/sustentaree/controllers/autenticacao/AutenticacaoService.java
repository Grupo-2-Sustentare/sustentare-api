package com.example.sustentaree.controllers.autenticacao;

import com.example.sustentaree.controllers.autenticacao.dto.UsuarioDetalhesDto;
import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {
  @Autowired
  private UsuarioRepository usuarioRepository;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Usuario> usuarioOpt = usuarioRepository.findByNomeAndAtivoTrue(username);

    if (usuarioOpt.isEmpty()){

      throw new UsernameNotFoundException(String.format("usuario: %s nao encontrado", username));
    }

    return new UsuarioDetalhesDto(usuarioOpt.get());
  }
}
