package isd.alprserver.service;

import isd.alprserver.dto.CompanyDTO;
import isd.alprserver.model.Company;
import isd.alprserver.model.User;

import java.util.List;

public interface CompanyService {
    List<CompanyDTO> getAllCompanies();

    Company addCompany(Company company);

    void deleteCompany(long id);

    List<Company> getCompanies();

    Company getCompanyById(long id);

    Company updateCompany(Company company);

    Company getByName(String name);

}
