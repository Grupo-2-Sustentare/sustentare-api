package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.usuario.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
=======
import org.springframework.data.jpa.repository.Query;
>>>>>>> 149f721000dfd0ce23a9829b4838df0b4d611883

import java.util.List;
import java.util.Optional;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByNomeAndAtivoTrue(String nome);
    Optional<Usuario> findByNome(String nome);

    @Query("SELECT MAX(u) FROM Usuario u")
    Integer findMaxIdUsuario();

    List<Usuario> findByAtivoTrue();

<<<<<<< HEAD
    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.ativo = :ativo WHERE u.id = :id")
    void updateAtivoById(@Param("ativo") Boolean ativo, @Param("id") Integer id);
=======
>>>>>>> 149f721000dfd0ce23a9829b4838df0b4d611883
}
