package isd.alprserver.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import isd.alprserver.model.Company;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
   //Company updateCompanyById(long id);
    Optional<Company> getByName(String name);
    Optional<Company> getByNameIgnoreCase(String name);
}
