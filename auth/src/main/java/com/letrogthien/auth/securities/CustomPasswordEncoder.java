package com.letrogthien.auth.securities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomPasswordEncoder {
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

