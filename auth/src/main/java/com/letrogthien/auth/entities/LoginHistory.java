package com.letrogthien.auth.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "login_history"
)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginHistory {
    @Id
    @GeneratedValue(
            strategy = GenerationType.UUID
    )
    private UUID id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;
    @Column(
            name = "login_at",
            nullable = false
    )
    private ZonedDateTime loginAt = ZonedDateTime.now();
    @Column(
            name = "ip_address",
            length = 45
    )
    private String ipAddress;
    @Column(
            name = "success",
            nullable = false
    )
    private boolean success;


    @PrePersist
    private void onCreate() {
        this.loginAt = ZonedDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        this.loginAt = ZonedDateTime.now();
    }

}
