package org.vaadin.example.service;

import org.springframework.stereotype.Service;
import org.vaadin.example.backend.entity.Company;
import org.vaadin.example.backend.repository.CompanyRepository;

import java.util.List;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> findAll() {
        return companyRepository.findAll();
    }
}