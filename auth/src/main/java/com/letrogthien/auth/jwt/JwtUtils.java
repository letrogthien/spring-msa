package com.letrogthien.auth.jwt;

import com.letrogthien.auth.common.TokenType;
import com.letrogthien.auth.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtTokenFactory jwtTokenFactory;

    public String generateToken(User user) {
        return this.jwtTokenFactory.createToken(user, TokenType.ACCESS_TOKEN);
    }

    public String generateRefreshToken(User user) {
        return this.jwtTokenFactory.createToken(user, TokenType.REFRESH_TOKEN);
    }

    public String generateTmpToken(User user) {
        return this.jwtTokenFactory.createToken(user, TokenType.TMP_TOKEN);
    }

    public String generateActivationToken(User user) {
        return this.jwtTokenFactory.createToken(user, TokenType.ACTIVATION_TOKEN);
    }

    public String generateResetPasswordToken(User user) {
        return this.jwtTokenFactory.createToken(user, TokenType.PASSWORD_RESET_TOKEN);
    }

    public boolean isTokenValid(String token, TokenType tokenType) {
        return this.jwtTokenFactory.isTokenValid(token, tokenType);
    }

    public String extractClaim(String token, String claim) {
        return this.jwtTokenFactory.extractClaim(token, claim);
    }

}
