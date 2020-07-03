package isd.alprserver.service;

import isd.alprserver.model.ParkingHistory;

import java.util.List;

public interface ParkingHistoryService {
    void save(ParkingHistory history);
    void update(ParkingHistory history);
    List<ParkingHistory> getAll();
    ParkingHistory getByDateAndCompanyId(String date, long companyId);

}
