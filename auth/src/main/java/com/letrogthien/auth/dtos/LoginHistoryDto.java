package com.letrogthien.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.UUID;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginHistoryDto {
    private UUID id;
    private UUID userId;
    private ZonedDateTime loginAt;
    private String ipAddress;
    private String deviceInfo;
    private boolean success;
}
