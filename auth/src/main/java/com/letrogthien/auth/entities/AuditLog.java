package com.letrogthien.auth.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;


@Entity
@Table(
        name = "audit_logs"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuditLog {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;
    @JoinColumn(
            name = "user_id"
    )
    private UUID userId;
    @Column(
            name = "action",
            nullable = false,
            length = 100
    )
    private String action;
    @Column(
            name = "description"
    )
    private String description;
    @Column(
            name = "created_at",
            nullable = false
    )
    private ZonedDateTime createdAt = ZonedDateTime.now();


    @PrePersist
    private void prePersist() {
        this.createdAt = ZonedDateTime.now();
    }

    @PreUpdate
    private void preUpdate() {
        this.createdAt = ZonedDateTime.now();
    }
}
