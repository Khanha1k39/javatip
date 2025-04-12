package com.example.demo.controller;

import com.example.demo.domain.dto.LoginDto;
import com.example.demo.services.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtService jwtService;
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtService jwtService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login( @Valid  @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
       String accessToken =  this.jwtService.createToken(authentication);
        return ResponseEntity.ok().body(accessToken);

    }
}
