package isd.alprserver.services;

import isd.alprserver.model.ParkingPlan;
import isd.alprserver.repositories.ParkingPlanRepository;
import isd.alprserver.services.interfaces.ParkingPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingPlanServiceImpl implements ParkingPlanService {

    private final ParkingPlanRepository parkingPlanRepository;

    @Override
    public Optional<ParkingPlan> findPlanById(int id) {
        return parkingPlanRepository.findById(id);
    }

    @Override
    public void add(ParkingPlan parkingPlan) {
        this.parkingPlanRepository.save(parkingPlan);
    }
}
