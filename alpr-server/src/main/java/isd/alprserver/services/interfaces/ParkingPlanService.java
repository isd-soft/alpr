package isd.alprserver.services.interfaces;

import isd.alprserver.model.ParkingPlan;

import java.util.Optional;

public interface ParkingPlanService {
    Optional<ParkingPlan> findPlanById(int id);
    void add(ParkingPlan parkingPlan);
}
