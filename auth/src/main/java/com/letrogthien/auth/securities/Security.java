package com.letrogthien.auth.securities;



import com.letrogthien.auth.common.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
public class Security {
    private final CustomJwtDecoder jwtDecoder;
    private final CustomAuthenticatinConverter converter;
    private final CustomAuthenticationEntryPoint entryPoint;
    private final GetTokenResolver getTokenResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        configureAuthorizationRules(security);
        configureSessionManagement(security);
        configureOAuth2ResourceServer(security);
        return security.build();
    }

    private void configureAuthorizationRules(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(authorize ->
            authorize
                    .requestMatchers(
                            "/api/v1/auth/login",
                            "/api/v1/auth/register",
                            "/api/v1/auth/verify-2fa",
                            "/api/v1/search/user/name",
                            "/api/v1/userCenter/forget-password",
                            "/api/v1/user/**",
                            "/swagger-ui/**",
                            "/v3/api-docs/**",
                            "/api/v1/auth/activate",
                            "/api/v1/auth/test"
                    ).permitAll()
                    .requestMatchers("/api/admin/**").hasAuthority(RoleName.ROLE_ADMIN.name())
                    .requestMatchers("/api/v1/auth/test-authenticated").hasAuthority(RoleName.ROLE_USER.name())
                    .anyRequest().authenticated()
        );
    }

    private void configureSessionManagement(HttpSecurity security) throws Exception {
        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureOAuth2ResourceServer(HttpSecurity security) throws Exception {
        security.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .decoder(jwtDecoder)
                        .jwtAuthenticationConverter(converter)
                )
                .bearerTokenResolver(getTokenResolver)
                .authenticationEntryPoint(entryPoint)
        );
    }

}
