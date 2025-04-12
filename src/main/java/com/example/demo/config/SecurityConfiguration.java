package com.example.demo.config;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static com.example.demo.services.JwtService.JWT_ALGORITHM;

@Configuration
@EnableMethodSecurity()
public class SecurityConfiguration {
    @Value("${JWT_BASE_64}")
    private String jwtKey;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests -> authorizeRequests.
                requestMatchers("/login").permitAll()
                .anyRequest().authenticated());
        http.oauth2ResourceServer((oauth2) -> oauth2.jwt(Customizer.withDefaults()));

        http.formLogin(AbstractHttpConfigurer::disable);
//        http.addFilterAfter(new JWTTokenGenaratorFilter(), BasicAuthenticationFilter.class);
//        http.addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class);

        http.httpBasic(Customizer.withDefaults());
        return  http.build();
    }

    private SecretKey getSecretKey() {
        // Giải mã chuỗi Base64 thành mảng byte
        byte[] keyBytes = Base64.getDecoder().decode(jwtKey);

        // Tạo SecretKeySpec từ mảng byte và thuật toán JWT
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }
    @Bean
    JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(getSecretKey()));
    }
    @Bean
    public JwtDecoder jwtDecoder() {
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(
                getSecretKey()
        ).macAlgorithm(JWT_ALGORITHM).build();

        return token -> {
            try {
                return jwtDecoder.decode(token);
            } catch (Exception e) {
                System.out.println(">>> JWT error: " + e.getMessage());
                throw e;
            }
        };
    }
//    @Bean
//    public JwtAuthenticationConverter jwtAuthenticationConverter() {
//        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
//        grantedAuthoritiesConverter.setAuthorityPrefix(authorityPrefix); // ví dụ: "ROLE_"
//        grantedAuthoritiesConverter.setAuthoritiesClaimName(authoritiesClaimName); // ví dụ: "roles"
//
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
//
//        return jwtAuthenticationConverter;
//    }

}
