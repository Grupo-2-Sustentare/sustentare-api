package com.example.sustentaree.repositories;

import com.example.sustentaree.domain.audit.ItemAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemAuditRepository extends JpaRepository<ItemAudit, Integer> {
}
