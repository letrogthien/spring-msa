package com.letrogthien.auth.requests;

import lombok.Data;

@Data
public class Verify2FaRequest {
    private String otp;
    private String secret;
}
