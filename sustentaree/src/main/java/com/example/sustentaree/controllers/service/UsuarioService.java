package com.example.sustentaree.controllers.service;

import com.example.sustentaree.configuration.security.jwt.GerenciadorTokenJwt;
import com.example.sustentaree.controllers.autenticacao.dto.UsuarioLoginDto;
import com.example.sustentaree.controllers.autenticacao.dto.UsuarioTokenDto;
import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.mapper.UsuarioMapper;
import com.example.sustentaree.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UsuarioService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private GerenciadorTokenJwt gerenciadorTokenJwt;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository usuarioRepository;
//    public void criar(UsuarioCriacaoDto usuarioCriacaoDto){
//         final Usuario novoUsuario = UsuarioMapper.of(usuarioCriacaoDto);
//           String senhaCriptografada = passwordEncoder.encode(toUsuario.getSenha());
//           toUsuario.setSenha(senhaCriptografada);

//         this.usuarioRepository.save(novoUsuario);
//    }

    public UsuarioTokenDto autenticar(UsuarioLoginDto usuarioLoginDto){
        final UsernamePasswordAuthenticationToken credetials = new UsernamePasswordAuthenticationToken(
                usuarioLoginDto.getNome(), usuarioLoginDto.getSenha());
        final Authentication authentication = this.authenticationManager.authenticate(credetials);

        Usuario usuarioAutenticado =
                usuarioRepository.findByNome(usuarioLoginDto.getNome())
                        .orElseThrow(
                                () -> new ResponseStatusException(404, "Nome do usuário não cadastrado", null)
                        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = gerenciadorTokenJwt.generateToken(authentication);

        return UsuarioMapper.of(usuarioAutenticado, token);
    }

}
