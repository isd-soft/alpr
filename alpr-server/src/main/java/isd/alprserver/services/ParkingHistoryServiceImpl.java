package isd.alprserver.services;

import isd.alprserver.model.ParkingHistory;
import isd.alprserver.model.exceptions.CompanyNotFoundException;
import isd.alprserver.repositories.ParkingHistoryRepository;
import isd.alprserver.services.interfaces.ParkingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingHistoryServiceImpl implements ParkingHistoryService {

    private final ParkingHistoryRepository parkingHistoryRepository;

    @Override
    public void save(ParkingHistory history) {
        parkingHistoryRepository.save(history);
    }

    @Override
    @Transactional
    public void update(ParkingHistory history) {
        Optional<ParkingHistory> parkingHistory = parkingHistoryRepository.findById(history.getId());
        parkingHistory.ifPresent(value -> value.setNrParkingSpots(history.getNrParkingSpots()));
    }

    @Override
    public List<ParkingHistory> getAll() {
        return parkingHistoryRepository.findAll();
    }

    @Override
    public ParkingHistory getByDateAndCompanyId(LocalDate date, long companyId) {
        return parkingHistoryRepository.findByDateAndCompanyId(date, companyId).orElseThrow(() -> new CompanyNotFoundException("Invalid company id"));
    }

    public List<ParkingHistory> getAllForToday() {
        return parkingHistoryRepository.findAllByDate(LocalDate.now());
    }
}
