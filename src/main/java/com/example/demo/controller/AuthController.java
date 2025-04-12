package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.dto.LoginDto;
import com.example.demo.domain.dto.ResLoginDto;
import com.example.demo.services.JwtService;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
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
    private final UserService userService;

    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, JwtService jwtService, UserService userService) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResLoginDto> login(@Valid  @RequestBody LoginDto loginDto) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
       String accessToken =  this.jwtService.createAccessToken(authentication);
       ResLoginDto resLoginDto = new ResLoginDto();
       resLoginDto.setAccess_token(accessToken);
       User user = userService.handleGetUserByEmail(loginDto.getEmail());
       ResLoginDto.UserLogin userLogin = new ResLoginDto.UserLogin();
       userLogin.setEmail(user.getEmail());
       userLogin.setName(user.getName());
       userLogin.setId(user.getId());
       resLoginDto.setUserLogin(userLogin);

       String refresh_token = this.jwtService.createRefreshToken(loginDto.getEmail(),resLoginDto);
    this.userService.updateUserToken(refresh_token,loginDto.getEmail());
    ResponseCookie cookie = ResponseCookie.from("refresh_token",refresh_token).maxAge(3600).build();
        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE,cookie.toString())
                .body(resLoginDto);

    }
}
