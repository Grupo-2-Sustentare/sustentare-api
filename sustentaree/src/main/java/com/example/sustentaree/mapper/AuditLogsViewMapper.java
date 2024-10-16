package com.example.sustentaree.mapper;

import com.example.sustentaree.domain.audit_logs_view.AuditLogsView;
import com.example.sustentaree.dtos.audit_logs_view.AuditLogsViewDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public abstract class AuditLogsViewMapper {
    public static final AuditLogsViewMapper INSTANCE = Mappers.getMapper(AuditLogsViewMapper.class);

    public abstract AuditLogsView toAuditLogsView (AuditLogsViewDTO auditLogsViewDTO);
    public abstract AuditLogsViewDTO toAuditLogsViewDTO(AuditLogsView auditLogsView);
    public abstract List<AuditLogsView> toAuditLogsViewList(List<AuditLogsViewDTO> auditLogsViewDTOList);
    public abstract List<AuditLogsViewDTO> toAuditLogsViewDTOList(List<AuditLogsView> auditLogsViewList);
}
