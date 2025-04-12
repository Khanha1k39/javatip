package com.example.demo.domain.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class LoginDto
{
    @Email(message = "Email")
    private String email;
    private String password;

}
