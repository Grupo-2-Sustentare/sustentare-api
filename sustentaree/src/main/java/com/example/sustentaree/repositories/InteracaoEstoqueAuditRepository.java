package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.audit.InteracaoEstoqueAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteracaoEstoqueAuditRepository extends JpaRepository<InteracaoEstoqueAudit, Integer> {
}
