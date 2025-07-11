package com.letrogthien.auth.jwt;

import com.letrogthien.auth.common.TokenType;
import com.letrogthien.auth.entities.User;
import com.letrogthien.auth.exceptions.CustomException; // Assuming you have a CustomException
import com.letrogthien.auth.exceptions.ErrorCode;   // And an ErrorCode enum/class
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
@RequiredArgsConstructor
public class JwtUtils {
    private final JwtTokenFactory jwtTokenFactory;

    public String generateToken(User user){
        try {
            return this.jwtTokenFactory.createToken(user, TokenType.ACCESS_TOKEN);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    public String generateRefreshToken(User user) {
        try {
            return this.jwtTokenFactory.createToken(user, TokenType.REFRESH_TOKEN);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    public String generateTmpToken(User user) {
        try {
            return this.jwtTokenFactory.createToken(user, TokenType.TMP_TOKEN);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    public String generateActivationToken(User user) {
        try {
            return this.jwtTokenFactory.createToken(user, TokenType.ACTIVATION_TOKEN);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    public String generateResetPasswordToken(User user) {
        try {
            return this.jwtTokenFactory.createToken(user, TokenType.PASSWORD_RESET_TOKEN);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new CustomException(ErrorCode.TOKEN_GENERATION_FAILED);
        }
    }

    public boolean isTokenValid(String token, TokenType tokenType) {
        return this.jwtTokenFactory.isTokenValid(token, tokenType);
    }

    public String extractClaim(String token, String claim) {
        return this.jwtTokenFactory.extractClaim(token, claim);
    }
}