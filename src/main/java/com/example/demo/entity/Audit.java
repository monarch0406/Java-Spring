package com.example.demo.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

// src/main/java/com/example/demo/entity/Audit.java
@Entity
@Table(name = "audit")
public class Audit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "column_name", length = 255)
    private String columnName;

    @Column(name = "before_value", length = 255)
    private String beforeValue;

    @Column(name = "after_value", length = 255)
    private String afterValue;

    @Column(name = "created_at", nullable = false,
            columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime createdAt = LocalDateTime.now();

    /* -------- getters / setters -------- */
    public Long getId() { return id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getColumnName() { return columnName; }
    public void setColumnName(String columnName) { this.columnName = columnName; }

    public String getBeforeValue() { return beforeValue; }
    public void setBeforeValue(String beforeValue) { this.beforeValue = beforeValue; }

    public String getAfterValue() { return afterValue; }
    public void setAfterValue(String afterValue) { this.afterValue = afterValue; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}

