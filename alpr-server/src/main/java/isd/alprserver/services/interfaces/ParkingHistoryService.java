package isd.alprserver.services.interfaces;

import isd.alprserver.model.ParkingHistory;

import java.time.LocalDate;
import java.util.List;

public interface ParkingHistoryService {
    void save(ParkingHistory history);

    void update(ParkingHistory history);

    List<ParkingHistory> getAll();

    ParkingHistory getByDateAndCompanyId(LocalDate date, long companyId);

    public List<ParkingHistory> getAllForToday();
}
