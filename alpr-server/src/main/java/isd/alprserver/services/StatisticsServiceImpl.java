package isd.alprserver.services;

import isd.alprserver.dtos.CompanyWithCarsDTO;
import isd.alprserver.dtos.ParkingHistoryDTO;
import isd.alprserver.model.Company;
import isd.alprserver.model.ParkingHistory;
import isd.alprserver.model.shared.CarStatisticsResponse;
import isd.alprserver.model.shared.UserStatisticsResponse;
import isd.alprserver.model.statistics.CarAudit;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.model.statistics.UserAudit;
import isd.alprserver.repositories.CarAuditRepository;
import isd.alprserver.repositories.ScanAuditRepository;
import isd.alprserver.repositories.UserAuditRepository;
import isd.alprserver.services.interfaces.CompanyService;
import isd.alprserver.services.interfaces.ParkingHistoryService;
import isd.alprserver.services.interfaces.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StatisticsServiceImpl implements StatisticsService {
    private final UserAuditRepository userAuditRepository;
    private final CarAuditRepository carAuditRepository;
    private final ScanAuditRepository scanAuditRepository;
    private final CompanyService companyService;
    private final ParkingHistoryService parkingHistoryService;

    @Override
    public void auditUserRegistration(UserAudit userAudit) {
        userAuditRepository.save(userAudit);
    }

    @Override
    public void auditCarRegistration(CarAudit carAudit) {
        carAuditRepository.save(carAudit);
    }

    @Override
    public CarStatisticsResponse getCarStatistics() {
        return CarStatisticsResponse.builder()
                .carsNumber(getAllRegisteredCarsEverNumber())
                .scansNumber(getAllPlatesScannedEverNumber())
                .build();
    }

    private long getAllRegisteredUsersEverNumber() {
        return userAuditRepository.count();
    }

    private long getAllRegisteredCarsEverNumber() {
        return carAuditRepository.count();
    }

    private long getAllPlatesScannedEverNumber() {
        return scanAuditRepository.count();
    }

    @Override
    public UserStatisticsResponse getUserStatistics() {
        return UserStatisticsResponse.builder()
                .peopleNumber(getAllRegisteredUsersEverNumber())
                .build();
    }

    @Override
    public int getTotalNrAllowedCars() {
        return scanAuditRepository.countAllByStatusAndIsAllowed("IN", true);
    }

    @Override
    public int getTotalNrRejectedCars() {
        return scanAuditRepository.countAllByStatusAndIsAllowed("IN", false);
    }

    @Override
    public void addScanAudit(ScanAudit scanAudit) {
        this.scanAuditRepository.save(scanAudit);
    }

    @Override
    public List<ScanAudit> getAllInLastWeek() {
        return scanAuditRepository.findAllInLastWeek();
    }

    @Override
    public List<ScanAudit> getAllAllowedLastWeek() {
        return scanAuditRepository.findAllAllowedLastWeek();
    }


    @Override
    public List<CompanyWithCarsDTO> getCarsPerCompany() {
        return companyService.getAll() .stream()
                .map(company -> CompanyWithCarsDTO.
                        builder()
                        .name(company.getName())
                        .cars(
                                company.getUsers().stream()
                                        .map(user -> user.getCars().size())
                                        .reduce(0, Integer::sum)
                        )
                        .build()
                )
                .collect(Collectors.toList());
    }

    @Override
    public List<ScanAudit> getAllScansForToday() {
        LocalDate today = LocalDate.now();
        return scanAuditRepository.findAll()
                .stream()
                .filter(audit -> audit.getScanDate().getDayOfWeek() == today.getDayOfWeek() && audit.getScanDate().getMonth() == today.getMonth() && audit.getScanDate().getYear() == today.getYear())
                .collect(Collectors.toList());
    }

    @Override
    public List<ParkingHistoryDTO> getAllParkingHistoriesForToday() {
        List<ParkingHistory> parkingHistories = parkingHistoryService.getAllForToday();
        ArrayList<ParkingHistoryDTO> parkingHistoryDTOS = new ArrayList<>();
        for (ParkingHistory parkingHistory : parkingHistories) {
            Company company = companyService.getById(parkingHistory.getCompanyId());
            parkingHistoryDTOS.add(ParkingHistoryDTO.builder()
                    .leftParkingSpots(parkingHistory.getNrParkingSpots())
                    .totalParkingSpots(company.getNrParkingSpots())
                    .companyName(company.getName())
                    .build());
        }
        return parkingHistoryDTOS;
    }
}
