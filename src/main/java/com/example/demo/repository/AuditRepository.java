package com.example.demo.repository;

import com.example.demo.entity.Audit;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditRepository extends JpaRepository<Audit, Long> {
    /** 依照 createdAt 降冪，撈出指定 userId 的所有稽核紀錄 */
    List<Audit> findByUserIdOrderByCreatedAtDesc(Long userId);
}
