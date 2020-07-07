package isd.alprserver.service;

import isd.alprserver.repository.CarAuditRepository;
import isd.alprserver.repository.ScanAuditRepository;
import isd.alprserver.repository.UserAuditRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{
    private final UserAuditRepository userAuditRepository;
    private final CarAuditRepository carAuditRepository;
    private final ScanAuditRepository scanAuditRepository;
}
