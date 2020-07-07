package isd.alprserver.service;

import isd.alprserver.repositories.CarAuditRepository;
import isd.alprserver.repositories.ScanAuditRepository;
import isd.alprserver.repositories.UserAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{
    private final UserAuditRepository userAuditRepository;
    private final CarAuditRepository carAuditRepository;
    private final ScanAuditRepository scanAuditRepository;

    @Override
    public int getTotalNrAllowedCars() {
        return scanAuditRepository.findTotalNrByStatus("ALLOWED");
    }

    @Override
    public int getTotalNrRejectedCars() {
        return scanAuditRepository.findTotalNrByStatus("REJECTED");
    }
}
