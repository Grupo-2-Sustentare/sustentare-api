package com.example.sustentaree.services;

import com.example.sustentaree.configuration.security.jwt.GerenciadorTokenJwt;
import com.example.sustentaree.controllers.autenticacao.dto.UsuarioLoginDto;
import com.example.sustentaree.controllers.autenticacao.dto.UsuarioTokenDto;
import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.repositories.UsuarioRepository;
import com.example.sustentaree.services.SessaoUsuarioService;
import com.example.sustentaree.services.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.authentication.AuthenticationManager;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioServiceTest {

  @InjectMocks
  private UsuarioService usuarioService;

  @Mock
  private UsuarioRepository usuarioRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @Mock
  private GerenciadorTokenJwt gerenciadorTokenJwt;

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private SessaoUsuarioService sessaoUsuarioService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    usuarioService.setSessaoUsuarioService(sessaoUsuarioService);
  }

  @Test
  @DisplayName("Deve listar todos os usuários")
  public void testCriar() {
    Usuario usuario = new Usuario();
    usuario.setEmail("test@example.com");
    usuario.setSenha("password");

    when(usuarioRepository.findByNomeAndAtivoTrue(usuario.getEmail())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(usuario.getSenha())).thenReturn("encodedPassword");
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    Usuario result = usuarioService.criar(usuario, 1);

    assertEquals(usuario, result);
    verify(sessaoUsuarioService, times(1)).setCurrentUserSession(1);
  }

  @Test
  @DisplayName("Deve retornar erro ao criar usuário existente")
  public void testCriarUsuarioExistente() {
    Usuario usuario = new Usuario();
    usuario.setEmail("test@example.com");
    usuario.setSenha("password");

    when(usuarioRepository.findByNomeAndAtivoTrue(usuario.getEmail())).thenReturn(Optional.of(usuario));

    assertThrows(ResponseStatusException.class, () -> usuarioService.criar(usuario, 1));
  }

  @Test
  @DisplayName("Deve autenticar usuário")
  public void testAutenticar() {
    UsuarioLoginDto usuarioLoginDto = new UsuarioLoginDto();
    usuarioLoginDto.setNome("test");
    usuarioLoginDto.setSenha("password");

    Usuario usuario = new Usuario();
    usuario.setEmail("test@example.com");
    usuario.setSenha("password");

    Authentication auth = mock(Authentication.class);

    when(usuarioRepository.findByNomeAndAtivoTrue(usuarioLoginDto.getNome())).thenReturn(Optional.of(usuario));
    when(authenticationManager.authenticate(any())).thenReturn(auth);
    when(gerenciadorTokenJwt.generateToken(auth)).thenReturn("token");

    UsuarioTokenDto result = usuarioService.autenticar(usuarioLoginDto);

    assertNotNull(result);
    assertEquals("token", result.getToken());
  }

  @Test
  @DisplayName("Deve retornar erro ao autenticar usuário inexistente")
  public void testListar() {
    List<Usuario> usuarios = new ArrayList<>();
    usuarios.add(new Usuario());

    when(usuarioRepository.findByAtivoTrue()).thenReturn(usuarios);

    List<Usuario> result = usuarioService.listar();

    assertNotNull(result);
    assertEquals(1, result.size());
  }

  @Test
  @DisplayName("Deve retornar usuário por id")
  public void testPorId() {
    Usuario usuario = new Usuario();
    usuario.setId(1);

    when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

    Usuario result = usuarioService.porId(1);

    assertNotNull(result);
    assertEquals(1, result.getId());
  }

  @Test
  @DisplayName("Deve retornar erro ao buscar usuário inexistente")
  public void testPorIdNotFound() {
    when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> usuarioService.porId(1));
  }
  @Test
  @DisplayName("Deve atualizar usuário")
  public void testAtualizar() {
    Usuario usuario = new Usuario();
    usuario.setId(1);
    usuario.setEmail("test@example.com");
    usuario.setSenha("password");

    when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.encode(usuario.getSenha())).thenReturn("encodedPassword");
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    Usuario result = usuarioService.atualizar(usuario, 1, 1);

    assertNotNull(result);
    assertEquals(usuario, result);
  }

  @Test
  @DisplayName("Deve atualiuzar usuário de outro usuário")
  public void testAtualizarOutros() {
    Usuario usuario = new Usuario();
    usuario.setId(1);
    usuario.setEmail("test@example.com");
    usuario.setSenha("password");
    usuario.setAcesso(1);

    when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
    when(passwordEncoder.encode(usuario.getSenha())).thenReturn("encodedPassword");
    when(usuarioRepository.save(usuario)).thenReturn(usuario);

    Usuario result = usuarioService.atualizarOutros(1, usuario, 1, 1);

    assertNotNull(result);
    assertEquals(usuario, result);
  }

  @Test
  @DisplayName("Deve retornar erro ao atualizar usuário de outro usuário não autorizado")
  public void testAtualizarOutrosNotAuthorized() {
    Usuario usuario = new Usuario();
    usuario.setId(1);
    usuario.setEmail("test@example.com");
    usuario.setSenha("password");
    usuario.setAcesso(0);

    when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

    assertThrows(ResponseStatusException.class, () -> usuarioService.atualizarOutros(1, usuario, 1, 1));
  }

  @Test
  @DisplayName("Deve deletar usuário")
  public void testDeletar() {
    Usuario usuario = new Usuario();
    usuario.setId(1);

    when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));
    doNothing().when(usuarioRepository).delete(usuario);

    assertDoesNotThrow(() -> usuarioService.deletar(1, 1));
  }
  @Test
  @DisplayName("Deve retornar erro ao deletar usuário inexistente")
  public void testDeletarNotFound() {
    when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

    assertThrows(ResponseStatusException.class, () -> usuarioService.deletar(1, 1));
  }
}