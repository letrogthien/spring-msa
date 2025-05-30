package com.letrogthien.auth.requests;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ForgotPwdRequest {
    @Pattern(
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Email must be a valid email address."
    )
     private String email;
}
