package isd.alprserver.service;

import isd.alprserver.model.Company;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    Company addCompany(Company company);

    void deleteCompany(long id);

    List<Company> getCompanies();

    Company getCompanyById(long id);

    Company updateCompany(Company company);

    Company getByName(String name);
}
