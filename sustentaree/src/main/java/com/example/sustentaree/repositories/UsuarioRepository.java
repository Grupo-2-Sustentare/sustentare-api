package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNomeAndAtivoTrue(String nome);
    Optional<Usuario> findByNome(String nome);
    List<Usuario> findByAtivoTrue();

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.ativo = :ativo WHERE u.id = :id")
    void updateAtivoById(@Param("ativo") Boolean ativo, @Param("id") Integer id);
}
