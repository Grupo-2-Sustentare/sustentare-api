package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.audit_logs_view.AuditLogsView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogsViewRepository extends JpaRepository<AuditLogsView, Integer> {
}
