package com.example.demo.domain;

import com.example.demo.services.JwtService;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Instant;

@Data
@Table(name = "companies")
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Tên công ty không được để trống")
    @Size(min = 2, max = 100, message = "Tên công ty phải từ 2 đến 100 ký tự")
    private String name;

    private String address;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;
    private String logo;
    private Instant creationDate;
    private Instant modificationDate;
    private String createdBy;
    private String modifiedBy;

    @PrePersist
    public void handleCreatedAt() {
        this.createdBy = JwtService.getCurrentUserLogin().isPresent() ? JwtService.getCurrentUserLogin().get() : null;
        this.creationDate = Instant.now();
    }
    @PreUpdate
    public void handleupdatedAt() {
        this.modifiedBy = JwtService.getCurrentUserLogin().isPresent() ? JwtService.getCurrentUserLogin().get() : null;
        this.modificationDate = Instant.now();
    }


}
