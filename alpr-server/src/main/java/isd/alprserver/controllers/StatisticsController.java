package isd.alprserver.controllers;

import isd.alprserver.dtos.CompanyWithCarsDTO;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.dtos.AllowedRejectedCounterDTO;
import isd.alprserver.model.shared.CarStatisticsResponse;
import isd.alprserver.model.shared.UserStatisticsResponse;
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
}
