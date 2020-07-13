package isd.alprserver.services.interfaces;

import isd.alprserver.dtos.ParkingHistoryDTO;
import isd.alprserver.model.Company;
import isd.alprserver.model.shared.CarStatisticsResponse;
import isd.alprserver.model.shared.UserStatisticsResponse;
import isd.alprserver.model.statistics.CarAudit;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.model.statistics.UserAudit;

import java.util.List;

public interface StatisticsService {

    void auditUserRegistration(UserAudit userAudit);

    void auditCarRegistration(CarAudit carAudit);

    CarStatisticsResponse getCarStatistics();

    UserStatisticsResponse getUserStatistics();

    int getTotalNrAllowedCars();

    int getTotalNrRejectedCars();

    void addScanAudit(ScanAudit scanAudit);

    List<ScanAudit> getAllInLastWeek();

    List<ScanAudit> getAllAllowedLastWeek();

    List<Company> getAllCompanies();

    List<ScanAudit> getAllScansForToday();

    List<ParkingHistoryDTO> getAllParkingHistoriesForToday();
}
