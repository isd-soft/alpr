package isd.alprserver.services;

import isd.alprserver.dtos.CompanyDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.Company;
import isd.alprserver.model.exceptions.CompanyCreationException;
import isd.alprserver.model.exceptions.CompanyNotFoundException;
import isd.alprserver.repositories.CompanyRepository;
import isd.alprserver.services.interfaces.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    @Transactional
    public List<Company> getAll() {
        return companyRepository.findAll();
    }

    @Override
    @Transactional
    public Company add(Company company) throws CompanyCreationException {
        if (companyRepository.getByNameIgnoreCase(company.getName()).isPresent()) {
            throw new CompanyCreationException("The company with the name " + company.getName() + " already exists.");
        }
        company = companyRepository.save(company);
        return company;
    }

    @Override
    @Transactional
    public void delete(long id) {
        companyRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Company getById(long id) {
        return companyRepository.findById(id).orElseThrow(() -> new CompanyNotFoundException("Company with id = " + id + "not found"));
    }

    @Override
    @Transactional
    public void update(Long id, CompanyDTO company) {
        Company companyById = getById(id);
        companyById.setName(company.getName());
        companyById.setNrParkingSpots(company.getNrParkingSpots());
        companyById.setLogo(company.getLogo() != null ?
                Base64.getDecoder().decode(company.getLogo().split(",")[1])
                : null);
    }

    @Override
    @Transactional
    public Company getByName(String name) {
        return this.companyRepository.getByName(name).orElseThrow(() -> new CompanyNotFoundException("This company doesn't exist"));
    }
}
