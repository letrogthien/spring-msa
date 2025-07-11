package com.letrogthien.auth.securities;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import com.letrogthien.auth.exceptions.CustomException;
import com.letrogthien.auth.exceptions.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomJwtDecoder implements JwtDecoder {
    private final RSAPublicKey publicKey;

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
        return NimbusJwtDecoder.withPublicKey(this.publicKey).build();
    }

    private JwtDecoder googleJwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri("https://www.googleapis.com/oauth2/v3/certs").build();
    }
}
