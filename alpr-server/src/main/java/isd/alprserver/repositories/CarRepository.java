package isd.alprserver.repositories;

import isd.alprserver.model.Car;
import isd.alprserver.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByLicensePlate(String licensePlate);

    @Query(nativeQuery = true, value = "select * from cars c where c.license_plate in :licensePlateList limit 1")
    Optional<Car> findByLicensePlates(List<String> licensePlateList);

    @Query(nativeQuery = true, value = "SELECT * FROM cars c INNER JOIN users u ON c.user_id=u.id INNER JOIN companies co ON co.id=u.company_id WHERE co.name = :name")
    List<Car> findByCompanyName(@Param("name") String name);

    List<Car> findByStatus(Status status);
}

