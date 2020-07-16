package isd.alprserver.controllers;

import isd.alprserver.dtos.*;
import isd.alprserver.model.shared.CarStatisticsResponse;
import isd.alprserver.model.shared.UserStatisticsResponse;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.services.interfaces.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@CrossOrigin("*")
@RequestMapping("/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping("/all-statuses")
    public ResponseEntity<AllowedRejectedCounterDTO> getTotalNrAllowedRejectedCars() {
        return ResponseEntity.ok(
                AllowedRejectedCounterDTO
                        .builder()
                        .allowedCars(statisticsService.getTotalNrAllowedCars())
                        .rejectedCars(statisticsService.getTotalNrRejectedCars())
                        .build()

        );
    }

    @GetMapping("/cars")
    public ResponseEntity<CarStatisticsResponse> getCarsStatistics() {
        return ResponseEntity.ok(statisticsService.getCarStatistics());
    }

    @GetMapping("/users")
    public ResponseEntity<UserStatisticsResponse> getUsersStatistics() {
        return ResponseEntity.ok(statisticsService.getUserStatistics());
    }

    @GetMapping("/all-last-week")
    public ResponseEntity<List<ScanAuditDTO>> getLastWeek() {
        return ResponseEntity.ok(
                statisticsService.getAllInLastWeek().stream()
                .map(this::ScanAuditToScan)
                .collect(Collectors.toList())
        );
    }

    private ScanAuditDTO ScanAuditToScan(ScanAudit sa) {
        return ScanAuditDTO.builder()
        .id(sa.getId())
        .allowed(sa.isAllowed())
        .licensePlate(sa.getLicensePlate())
        .scanDate(sa.getScanDate().toLocalDate().toString())
        .status(sa.getStatus())
        .build();
    }

    @GetMapping("/all-allowed-last-week")
    public ResponseEntity<List<ScanAuditDTO>> getAllowedLastWeek() {
        return ResponseEntity.ok(
                statisticsService.getAllAllowedLastWeek()
                .stream()
                .map(this::ScanAuditToScan)
                .collect(Collectors.toList())
        );
    }

    @GetMapping("/cars-per-company")
    public ResponseEntity<List<CompanyWithCarsDTO>> getAllCompaniesWithCars() {
        return ResponseEntity.ok(
                statisticsService.getCarsPerCompany()
        );
    }

    @GetMapping("/cars-per-morning")
    public ResponseEntity<List<CarsPerHoursDTO>> getNumberOfCarsMorning() {
        return ResponseEntity.ok(
                statisticsService.getAllScansForToday().stream().map(
                        audit -> CarsPerHoursDTO.builder().hour(audit.getScanDate().getHour()).build()
                )
                        .filter(audit -> audit.getHour() < 11)
                        .collect(Collectors.groupingBy(CarsPerHoursDTO::getHour))
                        .entrySet().stream()
                        .map(entry -> CarsPerHoursDTO.builder().hour(entry.getKey()).cars(entry.getValue().size()).build())
                        .collect(Collectors.toList())

        );

    }

    @GetMapping("/cars-per-evening")
    public ResponseEntity<List<CarsPerHoursDTO>> getNumberOfCarsEvening() {
        return ResponseEntity.ok(
                statisticsService.getAllScansForToday().stream().map(
                        audit -> CarsPerHoursDTO.builder().hour(audit.getScanDate().getHour()).build()
                )
                        .filter(audit -> audit.getHour() > 17)
                        .collect(Collectors.groupingBy(CarsPerHoursDTO::getHour))
                        .entrySet().stream()
                        .map(entry -> CarsPerHoursDTO.builder().hour(entry.getKey()).cars(entry.getValue().size()).build())
                        .collect(Collectors.toList())

        );

    }

    @GetMapping("/parking-histories-today")
    public ResponseEntity<List<ParkingHistoryDTO>> getParkingHistoriesForToday() {
        return ResponseEntity.ok(statisticsService.getAllParkingHistoriesForToday());
    }
}
