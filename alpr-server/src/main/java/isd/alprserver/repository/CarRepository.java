package isd.alprserver.repository;

import isd.alprserver.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Boolean existsByLicensePlate(String licensePlate);
}

