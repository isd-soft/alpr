package isd.alprserver.repository;

import isd.alprserver.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findByLicensePlate(String licensePlate);

    @Query(nativeQuery = true, value = "select * from cars c where c.license_plate in :licensePlateList limit 1")
    Optional<Car> findByLicensePlates(List<String> licensePlateList);
}

