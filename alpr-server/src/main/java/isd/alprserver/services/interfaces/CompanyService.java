package isd.alprserver.services.interfaces;

import isd.alprserver.model.Company;
import isd.alprserver.model.exceptions.CompanyCreationException;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();

    Company addCompany(Company company) throws CompanyCreationException;

    void deleteCompany(long id);

    List<Company> getCompanies();

    Company getCompanyById(long id);

    Company updateCompany(Company company);

    Company getByName(String name);

}
