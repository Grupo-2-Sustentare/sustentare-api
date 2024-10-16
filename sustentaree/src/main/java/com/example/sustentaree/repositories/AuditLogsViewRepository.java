package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.audit_logs_view.AuditLogsView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AuditLogsViewRepository extends JpaRepository<AuditLogsView, Integer> {
    @Query("SELECT a FROM AuditLogsView a WHERE a.fkUsuario = :fkUsuario")
    List<AuditLogsView> findLogsByFkUsuario(@Param("fkUsuario") Integer fkUsuario);
}
