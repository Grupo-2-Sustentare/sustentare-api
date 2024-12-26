package com.example.sustentaree.services;

import com.example.sustentaree.domain.audit.ItemAudit;
import com.example.sustentaree.domain.audit.InteracaoEstoqueAudit;
import com.example.sustentaree.repositories.ItemAuditRepository;
import com.example.sustentaree.repositories.InteracaoEstoqueAuditRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditService {

    private final ItemAuditRepository itemAuditRepository;
    private final InteracaoEstoqueAuditRepository interacaoEstoqueAuditRepository;

    public AuditService(ItemAuditRepository itemAuditRepository, InteracaoEstoqueAuditRepository interacaoEstoqueAuditRepository) {
        this.itemAuditRepository = itemAuditRepository;
        this.interacaoEstoqueAuditRepository = interacaoEstoqueAuditRepository;
    }

    public void logItemAudit(String descricao, Integer fkItem, Integer fkUsuario) {
        ItemAudit audit = new ItemAudit(descricao, LocalDateTime.now(), fkItem, fkUsuario);
        itemAuditRepository.save(audit);
        System.out.println("ItemAudit log: " + audit);
    }

    public void logInteracaoEstoqueAudit(String descricao, Integer fkUsuario, Integer fkInteracaoEstoque, Integer fkProduto) {
        InteracaoEstoqueAudit audit = new InteracaoEstoqueAudit(descricao, LocalDateTime.now(), fkUsuario, fkInteracaoEstoque, fkProduto);
        interacaoEstoqueAuditRepository.save(audit);
        System.out.println("InteracaoEstoqueAudit log: " + audit);
    }
}
