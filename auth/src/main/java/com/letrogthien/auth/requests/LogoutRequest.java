package com.letrogthien.auth.requests;

import lombok.Data;

@Data
public class LogoutRequest {
    private String refreshToken;
}
