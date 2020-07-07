package isd.alprserver.controllers;

import isd.alprserver.dto.AllowedRejectedDTO;
import isd.alprserver.service.StatisticsService;
import isd.alprserver.model.shared.EverRegisteredResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @GetMapping("/all-statuses")
    public ResponseEntity<AllowedRejectedDTO> getTotalNrAllowedRejectedCars() {
        return ResponseEntity.ok(
                AllowedRejectedDTO
                .builder()
                .allowedCars(statisticsService.getTotalNrAllowedCars())
                .rejectedCars(statisticsService.getTotalNrRejectedCars())
                .build()

        );
    }

    @GetMapping("/register")
    private ResponseEntity<EverRegisteredResponse> getRegisterStatistics() {
        return ResponseEntity.ok(statisticsService.getRegisterStatistics());
    }
}
