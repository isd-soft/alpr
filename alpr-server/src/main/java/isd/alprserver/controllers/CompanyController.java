package isd.alprserver.controllers;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import isd.alprserver.dtos.CarDTO;
import isd.alprserver.dtos.CompanyDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.CompanyCreationException;
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
    public ResponseEntity<List<CompanyDTO>> getAllCompanies() {
        return ResponseEntity.ok(companyService.getAll()
                .stream()
                .map(this::CompanyToCompanyDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<Company> addCompany(@RequestBody @Valid CompanyDTO companyDTO) {
        Company company = Company.builder()
                .name(companyDTO.getName())
                .nrParkingSpots(companyDTO.getNrParkingSpots())
                .logo(companyDTO.getLogo() != null ?
                        Base64.getDecoder().decode(companyDTO.getLogo().split(",")[1]) :
                        null)
                .build();
        this.companyService.add(company);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.delete(id);
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable long id) {
        return companyService.getById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Company> updateCompany(@RequestBody CompanyDTO company, @PathVariable(name = "id") Long id) {
        companyService.update(id, company);
        return ResponseEntity.noContent().build();
    }

    private CompanyDTO CompanyToCompanyDTO(Company company) {
        return CompanyDTO.builder()
                .id(company.getId())
                .name(company.getName())
                .nrParkingSpots(company.getNrParkingSpots())
                .logo(company.getLogo() != null ?
                        Base64.getEncoder().encodeToString(company.getLogo()) :
                        null)
                .build();
    }
}
