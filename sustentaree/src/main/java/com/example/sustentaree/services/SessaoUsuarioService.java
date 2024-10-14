package com.example.sustentaree.services;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SessaoUsuarioService {
  @PersistenceContext
  private EntityManager entityManager;

  @Transactional
  public void setCurrentUserSession(int userId) {
    entityManager.createNativeQuery("SET @current_user_id = :userId")
        .setParameter("userId", userId)
        .executeUpdate();
  }
}
