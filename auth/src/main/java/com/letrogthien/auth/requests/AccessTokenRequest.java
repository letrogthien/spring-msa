package com.letrogthien.auth.requests;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenRequest {
    private String refreshToken;
}
