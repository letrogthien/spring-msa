package com.letrogthien.auth.jwt;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;
import com.letrogthien.auth.common.TokenType;
import com.letrogthien.auth.entities.User;

public interface JwtTokenFactory {
    String createToken(User user, TokenType tokenType) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException;

    String createToken(Map<String, Object> extraClaims, User user, TokenType tokenType) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException;

    boolean isTokenValid(String token, TokenType tokenType);

    Map<String, Object> extractClaims(String token);

    String extractClaim(String token, String claim);
}