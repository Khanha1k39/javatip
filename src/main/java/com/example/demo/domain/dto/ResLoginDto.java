package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResLoginDto {
    private String access_token;
    private UserLogin userLogin;
    @Data
    public static class UserLogin{
        private String email;
        private String name;
        private Long id;
    }
}
