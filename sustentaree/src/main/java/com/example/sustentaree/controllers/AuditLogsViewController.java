package com.example.sustentaree.controllers;

import com.example.sustentaree.domain.audit_logs_view.AuditLogsView;
import com.example.sustentaree.dtos.audit_logs_view.AuditLogsViewDTO;
import com.example.sustentaree.mapper.AuditLogsViewMapper;
import com.example.sustentaree.services.AuditLogsViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/audit-logs")
public class AuditLogsViewController {

    @Autowired
    private AuditLogsViewService auditLogsViewService;

    @GetMapping
    public ResponseEntity<List<AuditLogsViewDTO>> getAuditLogs() {
        List<AuditLogsView> auditLogs = auditLogsViewService.getAllAuditLogs();
        List<AuditLogsViewDTO> auditLogsDTO = AuditLogsViewMapper.INSTANCE.toAuditLogsViewDTOList(auditLogs);
        if (auditLogsDTO.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(auditLogsDTO);
    }
}
