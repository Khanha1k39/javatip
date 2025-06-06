package com.example.demo.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;

public class JWTTokenValidatorFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            try {
                Environment environment = getEnvironment();
                String secret = environment.getProperty("JWT_SECRET");
                assert secret != null;
                SecretKey key = Keys.hmacShaKeyFor(secret.getBytes());
                token = token.substring(7);
              Claims claims = (Claims) Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
              String username = String.valueOf(claims.get("username")) ;
              String authorities = String.valueOf(claims.get("authorities"));
              Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
              SecurityContextHolder.getContext().setAuthentication(authentication);

            }
            catch (Exception e) {
                e.printStackTrace();
                throw new BadCredentialsException("Invalid JWT");
            }

        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/login");

    }
}
