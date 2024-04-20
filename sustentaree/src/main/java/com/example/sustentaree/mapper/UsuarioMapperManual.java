package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.dtos.usuario.UsuarioDTO;

public class UsuarioMapperManual {
    public static Usuario toUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setSenha(usuarioDTO.getSenha());
        usuario.setAcesso(usuarioDTO.getAcesso());
        return usuario;
    }

    public static UsuarioDTO toUsuarioDTO(Usuario usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setSenha(usuario.getSenha());
        usuarioDTO.setAcesso(usuario.getAcesso());
        return usuarioDTO;
    }
}
