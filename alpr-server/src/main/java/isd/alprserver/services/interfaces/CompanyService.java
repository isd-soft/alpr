package isd.alprserver.services.interfaces;

import isd.alprserver.model.Company;
import isd.alprserver.model.exceptions.CompanyCreationException;

import java.util.List;

public interface CompanyService{
    List<Company> getAll();

    Company add(Company company) throws CompanyCreationException;

    void delete(long id);


    Company getById(long id);

    Company update(Company company);

    Company getByName(String name);

}
