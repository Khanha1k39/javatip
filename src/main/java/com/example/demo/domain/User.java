package com.example.demo.domain;

import com.example.demo.utils.enums.GenderEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Data
@Entity
@Table(name="users")
public class User {
    private  String name;
    private String password;
    private String email;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role;

    private int age;
    private String address;
    @Enumerated(EnumType.STRING)
    private GenderEnum gender;
    private String refreshToken;
    private Instant created_at;
    private Instant updated_at;
    private String created_by;
    private String updated_by;

}
