package isd.alprserver.repositories;

import isd.alprserver.model.Car;
import isd.alprserver.model.Company;
import isd.alprserver.model.ParkingPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParkingPlanRepository extends JpaRepository<ParkingPlan, Integer> {
}