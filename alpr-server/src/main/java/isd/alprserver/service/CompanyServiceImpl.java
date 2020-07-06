package isd.alprserver.service;

import isd.alprserver.model.Company;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.CompanyCreationException;
import isd.alprserver.model.exceptions.CompanyNotFoundException;
import isd.alprserver.model.exceptions.UserCreationException;
import isd.alprserver.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService
{

    private final CompanyRepository companyRepository;

    @Override
    public List<Company> getAllCompanies()
    {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public Company addCompany(Company company) throws CompanyCreationException {
        if (companyRepository.getByNameIgnoreCase(company.getName()).isPresent()) {
            throw new CompanyCreationException("The company with the name " + company.getName() + " already exists.");
        }
        company = companyRepository.save(company);
        return company;
    }

    @Override
    public void deleteCompany(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Company> getCompanies(){
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
