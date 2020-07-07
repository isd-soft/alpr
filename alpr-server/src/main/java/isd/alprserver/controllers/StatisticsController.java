package isd.alprserver.controllers;

import isd.alprserver.services.interfaces.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/statistics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_SYSTEM_ADMINISTRATOR')")
public class StatisticsController {
    private final StatisticsService statisticsService;
}
