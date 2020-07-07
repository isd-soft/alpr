package isd.alprserver.services;

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
}
