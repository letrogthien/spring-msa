package com.letrogthien.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDto {
    private UUID id;
    private UUID userId;
    private String action;
    private String description;
    private ZonedDateTime createdAt;
}
