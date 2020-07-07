package isd.alprserver.services;

import isd.alprserver.model.shared.EverRegisteredResponse;
import isd.alprserver.model.statistics.CarAudit;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.model.statistics.UserAudit;
import isd.alprserver.repositories.CarAuditRepository;
import isd.alprserver.repositories.ScanAuditRepository;
import isd.alprserver.repositories.UserAuditRepository;
import isd.alprserver.services.interfaces.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService {
    private final UserAuditRepository userAuditRepository;
    private final CarAuditRepository carAuditRepository;
    private final ScanAuditRepository scanAuditRepository;

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
    public EverRegisteredResponse getRegisterStatistics() {
        return EverRegisteredResponse.builder()
                .carsNumber(getAllRegisteredCarsEverNumber())
                .peopleNumber(getAllRegisteredUsersEverNumber())
                .scansNumber(getAllPlatesScannedEverNumber())
                .build();
    }

    private Long getAllRegisteredUsersEverNumber() {
        return userAuditRepository.countAll();
    }

    private Long getAllRegisteredCarsEverNumber() {
        return carAuditRepository.countAll();
    }

    private Long getAllPlatesScannedEverNumber() {
        return scanAuditRepository.countAll();
    }

}
