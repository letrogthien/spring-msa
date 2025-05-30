package com.letrogthien.auth.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.letrogthien.auth.common.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "users"
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;
    @Column(
            name = "username",
            unique = true,
            nullable = false,
            length = 50
    )
    private String username;
    @Column(
            name = "email",
            unique = true,
            nullable = false,
            length = 255
    )
    private String email;
    @Column(
            name = "password_hash",
            nullable = false,
            length = 255
    )
    private String passwordHash;
    @Column(
            name = "status",
            nullable = false,
            length = 20
    )
    private Status status;
    @Column(
            name = "two_factor_enabled"
    )
    private boolean twoFactorEnabled = false;
    @Column(
            name = "created_at",
            nullable = false
    )
    private ZonedDateTime createdAt = ZonedDateTime.now();
    @Column(
            name = "updated_at",
            nullable = false
    )
    private ZonedDateTime updatedAt = ZonedDateTime.now();
    @Column(
            name = "last_login_at"
    )
    private ZonedDateTime lastLoginAt;
    @ManyToMany(
            fetch = FetchType.LAZY
    )
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id"
            )
    )
    private List<Role> roles = new ArrayList<>();
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<KycDocument> kycDocuments = new ArrayList<>();
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private List<LoginHistory> loginHistories = new ArrayList<>();
    @OneToMany(
            mappedBy = "user",
            cascade = {CascadeType.ALL},
            orphanRemoval = true
    )
    private List<AuditLog> auditLogs;

    @PreUpdate
    private void onUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    @PrePersist
    private void onCreate() {
        this.createdAt = ZonedDateTime.now();
        this.updatedAt = ZonedDateTime.now();
        this.lastLoginAt = ZonedDateTime.now();
        this.status = Status.PENDING;
        this.twoFactorEnabled = false;
    }

    public void updateLastLogin() {
        this.lastLoginAt = ZonedDateTime.now();
    }
}
