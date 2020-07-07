package isd.alprserver.controllers;

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

    //getting all the companies
    @GetMapping
    public  ResponseEntity<List<CompanyDTO>> getAll(){
        System.out.println("Inside Home Controller");
        return ResponseEntity.ok(companyService.getAllCompanies().stream().map(
                company -> CompanyDTO.builder().id(company.getId()).name(company.getName()).nrParkingSpots(company.getNrParkingSpots()).build()
        ).collect(Collectors.toList()));
    }

    //adding a company
    @PostMapping("/add")
    public ResponseEntity<Company> add(@RequestBody @Valid CompanyDTO companyDTO) {
        Company company = new Company();
        company.setName(companyDTO.getName());
        company.setNrParkingSpots(companyDTO.getNrParkingSpots());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(companyService.addCompany(company));
    }

    //deleting a company
    @DeleteMapping("/{id}")
    public void deleteCompany(@PathVariable long id) {
        companyService.deleteCompany(id);
    }

    //get the company with a specific id
    @GetMapping("/{id}")
    public Company getCompanyById(@PathVariable  long id){
       return companyService.getCompanyById(id);
    }

    //updating a company
    @PutMapping(value = "/update")
    public ResponseEntity<Company> update(@RequestBody CompanyDTO company){
        return ResponseEntity.ok(companyService.updateCompany(company.toCompany()));
    }
}
