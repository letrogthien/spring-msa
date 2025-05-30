package com.letrogthien.auth.jwt;

import java.util.HashMap;
import java.util.Map;
import com.letrogthien.auth.common.TokenType;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(
        prefix = "security.secret"
)
@Data
public class JwtTokenConfig {
    private Map<String, TokenConfig> tokens = new HashMap<>();

    @Getter
    @Setter
    public static class TokenConfig {
        private String secret;
        private long expiration;
    }

    public TokenConfig getTokenConfig(TokenType tokenType) {
        TokenConfig config = this.tokens.get(tokenType.name().toLowerCase());
        if (config == null) {
            throw new IllegalArgumentException("No configuration found for token type: " + tokenType);
        } else {
            return config;
        }
    }
}
