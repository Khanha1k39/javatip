package com.example.demo.services;

import com.example.demo.domain.Company;
import com.example.demo.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CompaniesService {

    CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }
    public Page<Company> getAllCompanies(int page, int size) {
        return companyRepository.findAll(PageRequest.of(page, size));
    }
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

}
