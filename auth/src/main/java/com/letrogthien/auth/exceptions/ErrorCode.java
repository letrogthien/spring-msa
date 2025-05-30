package com.letrogthien.auth.exceptions;



import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    INVALID_CREDENTIALS("AUTH_001", "Invalid username or password", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND("AUTH_002", "User not found", HttpStatus.NOT_FOUND),
    ACCOUNT_LOCKED("AUTH_003", "Account is locked", HttpStatus.FORBIDDEN),
    ACCOUNT_DISABLED("AUTH_004", "Account is disabled", HttpStatus.FORBIDDEN),
    TOKEN_EXPIRED("AUTH_005", "Authentication token has expired", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("AUTH_006", "Invalid authentication token", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED("AUTH_007", "Refresh token has expired", HttpStatus.UNAUTHORIZED),
    INVALID_REFRESH_TOKEN("AUTH_008", "Invalid refresh token", HttpStatus.UNAUTHORIZED),
    EMAIL_ALREADY_EXISTS("AUTH_009", "Email already registered", HttpStatus.CONFLICT),
    USERNAME_ALREADY_EXISTS("AUTH_010", "Username already taken", HttpStatus.CONFLICT),
    INVALID_PASSWORD_FORMAT("AUTH_011", "Password does not meet requirements", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_FORMAT("AUTH_012", "Invalid email format", HttpStatus.BAD_REQUEST),
    EMAIL_VERIFICATION_FAILED("AUTH_013", "Email verification failed", HttpStatus.BAD_REQUEST),
    VERIFICATION_CODE_EXPIRED("AUTH_014", "Verification code has expired", HttpStatus.GONE),
    INVALID_VERIFICATION_CODE("AUTH_015", "Invalid verification code", HttpStatus.BAD_REQUEST),
    PASSWORD_RESET_TOKEN_EXPIRED("AUTH_016", "Password reset token has expired", HttpStatus.GONE),
    INVALID_PASSWORD_RESET_TOKEN("AUTH_017", "Invalid password reset token", HttpStatus.BAD_REQUEST),
    PASSWORDS_DO_NOT_MATCH("AUTH_018", "New password and confirmation do not match", HttpStatus.BAD_REQUEST),
    MAX_LOGIN_ATTEMPTS_EXCEEDED("AUTH_019", "Maximum login attempts exceeded", HttpStatus.TOO_MANY_REQUESTS),
    SESSION_EXPIRED("AUTH_020", "Session has expired", HttpStatus.UNAUTHORIZED),
    ACCESS_DENIED("AUTH_021", "Access denied: insufficient permissions", HttpStatus.FORBIDDEN),
    UNAUTHORIZED("AUTH_022", "Unauthorized access", HttpStatus.UNAUTHORIZED),
    INVALID_CLAIM("AUTH_023", "Invalid claim in token", HttpStatus.UNAUTHORIZED),
    NOT_FOUND("AUTH_024", "Resource not found", HttpStatus.NOT_FOUND),
    PASSWORD_MISMATCH("AUTH_025", "password mismatch", HttpStatus.BAD_REQUEST),
    PASSWORD_RECENTLY_USED("AUTH_026", "password recently used", HttpStatus.BAD_REQUEST),
    INVALID_INPUT("AUTH_027", "Invalid input", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

}
