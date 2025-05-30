package com.letrogthien.auth.securities;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.text.ParseException;
import javax.crypto.SecretKey;
import com.letrogthien.auth.exceptions.CustomException;
import com.letrogthien.auth.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    @Value("${security.secret.tokens.access_token.secret}")
    private String secretKey;

    public Jwt decode(String token) throws JwtException {
        try {
            JWT jwt = JWTParser.parse(token);
            if (jwt.getJWTClaimsSet().getIssuer() != null) {
                String issuer = jwt.getJWTClaimsSet().getIssuer();
                if (issuer.contains("google")) {
                    return this.googleJwtDecoder().decode(token);
                }
            }

            return this.jwtDecoder().decode(token);
        } catch (ParseException var4) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
    }

    private JwtDecoder jwtDecoder() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);
        return NimbusJwtDecoder.withSecretKey(key).macAlgorithm(MacAlgorithm.HS256).build();
    }

    private JwtDecoder googleJwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();
    }
}
