package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.Query;


import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNomeAndAtivoTrue(String nome);
    Optional<Usuario> findByNomeAndAtivoTrueOrEmailAndAtivoTrue(String nome, String email);
    Optional<Usuario> findByNome(String nome);
    @Query("SELECT MAX(u) FROM Usuario u")
    Integer findMaxIdUsuario();

    List<Usuario> findByAtivoTrue();

    @Query("SELECT u FROM Usuario u WHERE u.id = :id")
    Optional<Usuario> findById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.ativo = :ativo WHERE u.id = :id")
    void updateAtivoById(@Param("ativo") Boolean ativo, @Param("id") Integer id);
}
