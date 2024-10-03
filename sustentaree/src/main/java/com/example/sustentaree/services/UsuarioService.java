package com.example.sustentaree.services;

import com.example.sustentaree.configuration.security.jwt.GerenciadorTokenJwt;
import com.example.sustentaree.controllers.autenticacao.dto.UsuarioLoginDto;
import com.example.sustentaree.controllers.autenticacao.dto.UsuarioTokenDto;
import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.mapper.UsuarioMapper;
import com.example.sustentaree.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {
  @Autowired
  private final UsuarioRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final GerenciadorTokenJwt gerenciadorTokenJwt;
  private final AuthenticationManager authenticationManager;

  @Autowired
  private SessaoUsuarioService sessaoUsuarioService;
  public UsuarioService(UsuarioRepository repository, PasswordEncoder passwordEncoder, GerenciadorTokenJwt gerenciadorTokenJwt, AuthenticationManager authenticationManager) {
    this.repository = repository;
    this.passwordEncoder = passwordEncoder;
    this.gerenciadorTokenJwt = gerenciadorTokenJwt;
    this.authenticationManager = authenticationManager;
  }
  @Transactional
  public Usuario criar(Usuario usuario, int idResponsavel){
    if(this.repository.findByNome(usuario.getEmail()).isPresent()){
      throw new ResponseStatusException(409, "Email de usuário já cadastrado", null);
    }
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);
    usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
    return this.repository.save(usuario);
  }

  public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto){
    final UsernamePasswordAuthenticationToken credetials = new UsernamePasswordAuthenticationToken(
        usuarioLoginDto.getNome(), usuarioLoginDto.getSenha());
    final Authentication authentication = this.authenticationManager.authenticate(credetials);

    Usuario usuarioAutenticado =
        this.repository.findByNome(usuarioLoginDto.getNome())
            .orElseThrow(
                () -> new ResponseStatusException(404, "Nome do usuário não cadastrado", null)
            );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final String token = this.gerenciadorTokenJwt.generateToken(authentication);

    return UsuarioMapper.of(usuarioAutenticado, token);
  }

  public List<Usuario> listar(){
    return this.repository.findByAtivoTrue();
  }

  public Usuario porId(Integer id){
    return this.repository.findById(id).orElseThrow(
        () -> new ResponseStatusException(404, "Usuário não encontrado", null)
    );
  }
  @Transactional
  public Usuario atualizar(Usuario usuario, int id, int idResponsavel) {
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    Usuario usuarioAtual = this.porId(id);

    usuario.setId(usuarioAtual.getId());
    usuario.setAcesso(usuarioAtual.getAcesso());

    if(!usuarioAtual.getEmail().equals(usuario.getEmail())){
      return this.criar(usuario, idResponsavel);
    }
    usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));

    return this.repository.save(usuario);
  }
  @Transactional
  public Usuario atualizarOutros (int idSolicitante, Usuario usuario, int id, int idResponsavel){
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    Usuario usuarioSolicitante = this.porId(idSolicitante);

    if (usuarioSolicitante.getAcesso() != 1){
      throw new ResponseStatusException(403, "Usuário não autorizado", null);
    }

    return this.atualizar(usuario, id, idResponsavel);
  }
  @Transactional
  public void deletar(int id, int idResponsavel){
    this.sessaoUsuarioService.setCurrentUserSession(idResponsavel);

    this.repository.updateAtivoById(false, id);
  }
  public void setSessaoUsuarioService(SessaoUsuarioService sessaoUsuarioService) {
    this.sessaoUsuarioService = sessaoUsuarioService;
  }

  public Integer getTotalUsuarios(){
    return this.repository.findAll().size();
  }

  public Integer getUltimoId(){
    return this.repository.findAll().get(this.repository.findAll().size() - 1).getId();
  }
}
