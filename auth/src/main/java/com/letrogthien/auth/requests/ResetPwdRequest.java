package com.letrogthien.auth.requests;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ResetPwdRequest {
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and be at least 8 characters long"
    )
    private String newPassword;
    private String confirmPassword;
}
