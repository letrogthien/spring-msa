package com.letrogthien.auth.requests;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RegisterRequest {
    @Pattern(
            regexp = "^[a-zA-Z0-9._-]{3,}$",
            message = "Username must be at least 3 characters long and can only contain letters, numbers, dots, underscores, and hyphens."
    )
    private String username;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one number."
    )
    private String password;
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email must be a valid email address."
    )
    private String email;

}
