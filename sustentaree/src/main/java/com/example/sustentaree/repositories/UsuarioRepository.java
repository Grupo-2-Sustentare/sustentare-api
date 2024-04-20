package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
}
