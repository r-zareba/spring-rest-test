package com.example.restfulwebservices.company;

import com.example.restfulwebservices.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService service;

    @GetMapping
    public List<Company> getAllUsers() {
        return service.findAll();
    }

    @GetMapping(path = "/{id}")
    public Company getById(@PathVariable int id) {
        Company company = service.getById(id);
        if (company == null) {
            throw new NotFoundException(String.format("Company id: %s not found", id));
        }
        return company;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody Company company) {
        Company savedCompany = service.save(company);

        // Add Location header in HTTP response
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedCompany.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Object> delete(@PathVariable int id) {
        boolean deleted = service.delete(id);
        if (!deleted) {
            throw new NotFoundException(String.format("Company id: %s not found", id));
        }
        return ResponseEntity.noContent().build();
    }

}