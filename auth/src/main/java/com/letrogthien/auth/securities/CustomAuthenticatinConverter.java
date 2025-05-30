package com.letrogthien.auth.securities;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.letrogthien.auth.common.TokenType;
import com.letrogthien.auth.entities.User;
import com.letrogthien.auth.exceptions.CustomException;
import com.letrogthien.auth.exceptions.ErrorCode;
import com.letrogthien.auth.repositories.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomAuthenticatinConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    private final UserRepository userRepository;

    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
        if (jwt.getClaim("type").equals(TokenType.REFRESH_TOKEN.toString())) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } else {
            List<GrantedAuthority> roles = this.extractAuthorities(jwt);
            return new JwtAuthenticationToken(jwt, roles);
        }
    }

    private List<GrantedAuthority> extractAuthorities(Jwt jwt) {
        return jwt.getClaim("iss") != null && jwt.getIssuer().toString().equals("https://accounts.google.com")
                ? this.extractAuthoritiesFromGGToken(jwt) : this.extractAuthoritiesFromToken(jwt);
    }

    private List<GrantedAuthority> extractAuthoritiesFromToken(Jwt jwt) {
        List<GrantedAuthority> roles = new ArrayList<>();
        if (jwt.getClaim("roles") != null) {
            List<String> roleList = jwt.getClaim("roles");
            roles = roleList.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }

        return roles;
    }

    private List<GrantedAuthority> extractAuthoritiesFromGGToken(Jwt jwt) {
        List<GrantedAuthority> roles = new ArrayList<>();
        String email = jwt.getClaim("email");
        if (email == null || email.isEmpty()) {
            return roles;
        }
        User user = this.userRepository.findByEmail(email).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        roles = user.getRoles().stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName().toString()))
                    .collect(Collectors.toList());
        return roles;
    }
}
