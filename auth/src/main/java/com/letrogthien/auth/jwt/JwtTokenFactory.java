package com.letrogthien.auth.jwt;

import java.util.Map;
import com.letrogthien.auth.common.TokenType;
import com.letrogthien.auth.entities.User;

public interface JwtTokenFactory {
    String createToken(User user, TokenType tokenType);

    String createToken(Map<String, Object> extraClaims, User user, TokenType tokenType);

    boolean isTokenValid(String token, TokenType tokenType);

    Map<String, Object> extractClaims(String token);

    String extractClaim(String token, String claim);
}