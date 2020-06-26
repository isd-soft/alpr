package isd.alprserver.service;

import java.util.List;

import isd.alprserver.model.exceptions.CompanyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import isd.alprserver.model.Company;
import isd.alprserver.repository.CompanyRepository;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company addCompany(Company company) {
        company = companyRepository.save(company);
        return company;
    }

    @Override
    public void deleteCompany(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public List<Company> getCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Company getCompanyById(long id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company with id = " + id + "not found"));
    }

    @Override
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company getByName(String name) {
        return this.companyRepository.getByName(name).orElseThrow(() -> new CompanyNotFoundException("This company doesn't exist"));
    }
}
