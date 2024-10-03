package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNomeAndAtivoTrue(String nome);
    Optional<Usuario> findByNome(String nome);
    List<Usuario> findByAtivoTrue();
}
