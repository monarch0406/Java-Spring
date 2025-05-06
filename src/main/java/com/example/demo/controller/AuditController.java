package com.example.demo.controller;

import com.example.demo.entity.Audit;
import com.example.demo.repository.AuditRepository;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/audits")
public class AuditController {

    private final AuditRepository auditRepo;

    public AuditController(AuditRepository auditRepo) {
        this.auditRepo = auditRepo;
    }

    /**  
     * GET /audits/{userId}  
     * 回傳該 userId 的所有 audit 紀錄，依時間由新到舊  
     */
    @GetMapping("/{userId}")
    public List<Audit> getByUser(@PathVariable Long userId) {
        return auditRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }
}

