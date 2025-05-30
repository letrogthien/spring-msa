package com.letrogthien.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(
        name = "password_history"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordHistory {
    @Id
    @GeneratedValue
    @Column(
            columnDefinition = "BINARY(16)"
    )
    private UUID id;
    @Column(
            name = "user_id",
            nullable = false,
            columnDefinition = "BINARY(16)"
    )
    private UUID userId;
    @Column(
            name = "password_hash",
            nullable = false,
            length = 255
    )
    private String passwordHash;
    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
    )
    private LocalDateTime createdAt;
    @Column(
            name = "current_index",
            nullable = false
    )
    private int currentIndex;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
