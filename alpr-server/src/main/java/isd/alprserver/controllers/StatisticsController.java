package isd.alprserver.controllers;

import isd.alprserver.dtos.AllowedRejectedCounterDTO;
import isd.alprserver.dtos.CarsPerHoursDTO;
import isd.alprserver.dtos.CompanyWithCarsDTO;
import isd.alprserver.dtos.ParkingHistoryDTO;
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
    public ResponseEntity<List<ScanAudit>> getLastWeek() {
        return ResponseEntity.ok(statisticsService.getAllInLastWeek());
    }

    @GetMapping("/all-allowed-last-week")
    public ResponseEntity<List<ScanAudit>> getAllowedLastWeek() {
        return ResponseEntity.ok(statisticsService.getAllAllowedLastWeek());
    }

    @GetMapping("/cars-per-company")
    public ResponseEntity<List<CompanyWithCarsDTO>> getAllCompaniesWithCars() {
        return ResponseEntity.ok(
                statisticsService.getAllCompanies()
                        .stream()
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
                        .collect(Collectors.toList())
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
