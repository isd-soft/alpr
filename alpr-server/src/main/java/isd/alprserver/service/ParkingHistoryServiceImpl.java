package isd.alprserver.service;

import isd.alprserver.model.ParkingHistory;
import isd.alprserver.model.exceptions.CompanyNotFoundException;
import isd.alprserver.repository.ParkingHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ParkingHistory getByDateAndCompanyId(String date, long companyId) {
        return parkingHistoryRepository.findByDateAndCompanyId(date, companyId).orElseThrow(() -> new CompanyNotFoundException("Invalid company id"));
    }
}
