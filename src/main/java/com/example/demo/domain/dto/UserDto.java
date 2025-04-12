package com.example.demo.domain.dto;

import com.example.demo.utils.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
public class UserDto {
    private  String name;
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    private int age;
    private String address;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private Instant created_at;
    private Instant updated_at;
    private String created_by;
    private String updated_by;
}
