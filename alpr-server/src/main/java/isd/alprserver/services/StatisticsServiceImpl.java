package isd.alprserver.services;

import isd.alprserver.model.Company;
import isd.alprserver.model.shared.CarStatisticsResponse;
import isd.alprserver.model.shared.UserStatisticsResponse;
import isd.alprserver.model.statistics.CarAudit;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.model.statistics.UserAudit;
import isd.alprserver.repositories.CarAuditRepository;
import isd.alprserver.repositories.ScanAuditRepository;
import isd.alprserver.repositories.UserAuditRepository;
import isd.alprserver.services.interfaces.CompanyService;
import isd.alprserver.services.interfaces.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.metrics.MetricsEndpoint;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final UserAuditRepository userAuditRepository;
    private final CarAuditRepository carAuditRepository;
    private final ScanAuditRepository scanAuditRepository;
    private final CompanyService companyService;
    private final HealthEndpoint healthEndpoint;
    private final MetricsEndpoint metricsEndpoint;

    @PostConstruct
    public void foo(){
        System.out.println(healthEndpoint.health());
        System.out.println(metricsEndpoint.listNames());
    }

    @Override
    public UserAudit auditUserRegistration(UserAudit userAudit) {
        return userAuditRepository.save(userAudit);
    }

    @Override
    public CarAudit auditCarRegistration(CarAudit carAudit) {
        return carAuditRepository.save(carAudit);
    }

    @Override
    public ScanAudit auditPlateScanning(ScanAudit scanAudit) {
        return scanAuditRepository.save(scanAudit);
    }

    @Override
    public CarStatisticsResponse getCarStatistics() {
        return CarStatisticsResponse.builder()
                .carsNumber(getAllRegisteredCarsEverNumber())
                .scansNumber(getAllPlatesScannedEverNumber())
                .build();
    }

    private long getAllRegisteredUsersEverNumber() {
        return userAuditRepository.countAll();
    }

    private long getAllRegisteredCarsEverNumber() {
        return carAuditRepository.countAll();
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
        return scanAuditRepository.findAllInLastWeek(new Date());
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }
}
