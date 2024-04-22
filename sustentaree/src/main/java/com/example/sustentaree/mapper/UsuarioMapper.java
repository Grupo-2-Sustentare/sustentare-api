package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.dtos.usuario.UsuarioDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


import java.util.List;
@Mapper
public abstract class UsuarioMapper {




    public static final UsuarioMapper INSTANCE = Mappers.getMapper(UsuarioMapper.class);


    public abstract Usuario toUsuario(UsuarioDTO usuarioDTO);
    public abstract UsuarioDTO toUsuarioDTO(Usuario usuario);
    public abstract List<UsuarioDTO> toUsuarioListDTO(List<Usuario> usuarios);









}
