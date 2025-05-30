package com.letrogthien.auth.requests;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
    String deviceName;
    String deviceType;
}
