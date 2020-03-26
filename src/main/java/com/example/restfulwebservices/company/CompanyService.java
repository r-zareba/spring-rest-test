package com.example.restfulwebservices.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository repository;

    private static List<Company> companies = new ArrayList<Company>() {
        {
            add(new Company(1, "Apple", "IT", "USA", 1200, new Date()));
            add(new Company(2, "IBM", "IT", "USA", 1400, new Date()));
            add(new Company(3, "EY", "Finance", "USA", 2200, new Date()));
        }
    };

    private static int usersCount = companies.size();

    public List<Company> findAll() {
        return companies;
    }

    public Company save(Company company) {
        if (company.getId() == null || company.getId() == 0) {
            company.setId(++usersCount);
        }
        companies.add(company);
        return company;
    }

    public Company getById(int id) {
        return companies.stream().filter(t -> t.getId() == id).findAny().orElse(null);
    }

    public boolean delete(int id) {
        return companies.removeIf(t -> t.getId() == id);
    }


}
