package com.letrogthien.auth.entities;

import com.letrogthien.auth.common.Status;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@Document("register_outbox")
public class RegisterOutBox {
    @Id
    private UUID id;
    private String email;
    private UUID userId;
    private String urlActivation;
    private Status status;
    private ZonedDateTime createdAt;

    @PrePersist
    private void prePersist() {
        this.createdAt = ZonedDateTime.now();
        this.status = Status.PENDING;
    }
}
