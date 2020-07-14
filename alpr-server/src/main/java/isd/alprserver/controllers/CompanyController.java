package isd.alprserver.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import isd.alprserver.dtos.CompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import isd.alprserver.model.Company;
import isd.alprserver.services.interfaces.CompanyService;

import javax.validation.Valid;


@RestController
@RequestMapping("/companies")
@CrossOrigin
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping()
    public  ResponseEntity<List<CompanyDTO>> getAllCompanies(){
        return ResponseEntity.ok(companyService.getAll().stream().map(
                company -> CompanyDTO.builder().id(company.getId()).name(company.getName()).nrParkingSpots(company.getNrParkingSpots()).build()
        ).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<Company> addCompany(@RequestBody @Valid CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setNrParkingSpots(companyDTO.getNrParkingSpots());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyService.add(company));
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.delete(id);
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable  long id){
       return companyService.getById(id);
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Company> updateCompany(@RequestBody CompanyDTO company){
        return ResponseEntity.ok(companyService.update(company.toCompany()));
    }
}
