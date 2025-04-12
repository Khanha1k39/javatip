package com.example.demo.controller;

import com.example.demo.domain.Company;
import com.example.demo.services.CompaniesService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/companies")
@RestController
public class CompanyController {

    private final CompaniesService companiesService;

    public CompanyController(CompaniesService companiesService) {
        this.companiesService = companiesService;
    }

    @PostMapping("")
    public ResponseEntity<Company> create(@Valid @RequestBody Company company) {
        return ResponseEntity.ok(companiesService.createCompany(company));
    }
    @GetMapping("")
    public ResponseEntity<Page<Company>> getAll(@RequestParam Optional< String> page, @RequestParam Optional<String> size) {
        String sCurrent = page.orElse("");
        String sSize = size.orElse("");

        return ResponseEntity.ok(companiesService.getAllCompanies(Integer.parseInt(sCurrent)-1,Integer.parseInt(sSize)));
    }
    @PutMapping
    public ResponseEntity<Company> update(@Valid @RequestBody Company company) {
        return ResponseEntity.ok(companiesService.updateCompany(company));
    }
}
