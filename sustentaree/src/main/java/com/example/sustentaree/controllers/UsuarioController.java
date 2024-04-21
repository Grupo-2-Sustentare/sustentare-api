package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.dtos.usuario.UsuarioDTO;
import com.example.sustentaree.mapper.UsuarioMapper;
import com.example.sustentaree.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        Usuario toUsuario = UsuarioMapper.INSTANCE.toUsuario(dto);
        Usuario usuarioSalvo = usuarioRepository.save(toUsuario);
        UsuarioDTO toDTO =  UsuarioMapper.INSTANCE.toUsuarioDTO(usuarioSalvo);
        return ResponseEntity.created(null).body(toDTO);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UsuarioDTO> toDTO = UsuarioMapper.INSTANCE.toUsuarioListDTO(usuarios);
        return ResponseEntity.ok(toDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(UsuarioMapper.INSTANCE.toUsuarioDTO(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/atualizar/{id}")
    @Transactional
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            Usuario usuarioAtualizado = UsuarioMapper.INSTANCE.toUsuario(dto);
            usuarioAtualizado.setId(usuario.get().getId());
            Usuario usuarioSalvo = usuarioRepository.save(usuarioAtualizado);
            return ResponseEntity.ok(UsuarioMapper.INSTANCE.toUsuarioDTO(usuarioSalvo));
        }
        return ResponseEntity.notFound().build();
    }


    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<UsuarioDTO> alterar(@PathVariable Integer id, @RequestBody UsuarioDTO dto) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            if (usuario.get().getAcesso() == 1) {
                Usuario usuarioAtualizado = UsuarioMapper.INSTANCE.toUsuario(dto);
                usuarioAtualizado.setId(usuario.get().getId());
                usuarioAtualizado.setAcesso(usuario.get().getAcesso());
                Usuario usuarioSalvo = usuarioRepository.save(usuarioAtualizado);
                return ResponseEntity.ok(UsuarioMapper.INSTANCE.toUsuarioDTO(usuarioSalvo));
            }else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}

