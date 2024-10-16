package com.example.sustentaree.services;

import com.example.sustentaree.domain.audit_logs_view.AuditLogsView;
import com.example.sustentaree.repositories.AuditLogsViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogsViewService {
    @Autowired private AuditLogsViewRepository auditLogsViewRepository;

    public List<AuditLogsView> getAllAuditLogs() {
        return auditLogsViewRepository.findAll();
    }
}
