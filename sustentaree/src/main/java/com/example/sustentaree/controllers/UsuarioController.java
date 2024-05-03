package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.usuario.Usuario;
import com.example.sustentaree.dtos.usuario.UsuarioDTO;
import com.example.sustentaree.mapper.UsuarioMapper;
import com.example.sustentaree.repositories.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Operation(summary = "Criar um usuário", description = "Cria um usuário com base nas informações fornecidas")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDTO.class)
        )),
        @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDTO.class)
        )),
        @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = UsuarioDTO.class)
        ))
})
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody UsuarioDTO dto) {
        Usuario toUsuario = UsuarioMapper.INSTANCE.toUsuario(dto);
        Usuario usuarioSalvo = usuarioRepository.save(toUsuario);
        UsuarioDTO toDTO =  UsuarioMapper.INSTANCE.toUsuarioDTO(usuarioSalvo);
        return ResponseEntity.created(null).body(toDTO);
    }
@Operation(summary = "Listar usuários", description = "Lista todos os usuários cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Nenhum usuário encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            ))
    })
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<UsuarioDTO> toDTO = UsuarioMapper.INSTANCE.toUsuarioListDTO(usuarios);
        return ResponseEntity.ok(toDTO);
    }
@Operation(summary = "Buscar um usuário por ID", description = "Retorna um usuário com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário retornado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class),
                    examples = @ExampleObject(
                            value = "{\n  \"nome\": \"TESTE FODA Exemplo\",\n  \"senha\": \"123456\",\n  \"acesso\": 1\n}"
                    )
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            ))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable Integer id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(UsuarioMapper.INSTANCE.toUsuarioDTO(usuario.get()));
        }
        return ResponseEntity.notFound().build();
    }
    @Operation(summary = "Atualizar um usuário", description = "Atualiza um usuário com base nas informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            ))
    })
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
    @Operation(summary = "Alterar um usuário", description = "Altera um usuário com base nas informações fornecidas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário alterado com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            ))
    })
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
    @Operation(summary = "Remover um usuário", description = "Remove um usuário com base no ID fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UsuarioDTO.class)
            ))
    })
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

