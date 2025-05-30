package com.letrogthien.auth.dtos;

import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.common.Status;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private UUID id;
    private String username;
    private String email;
    private Status status;
    private boolean twoFactorEnabled;
    private ZonedDateTime createdAt;
    private ZonedDateTime lastLoginAt;
    private List<RoleName> roleNames;
    private Status kycStatus;
}
