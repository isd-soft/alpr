package isd.alprserver.services.interfaces;

import isd.alprserver.model.shared.EverRegisteredResponse;
import isd.alprserver.model.statistics.CarAudit;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.model.statistics.UserAudit;

public interface StatisticsService {
    UserAudit auditUserRegistration(UserAudit userAudit);

    CarAudit auditCarRegistration(CarAudit carAudit);

    ScanAudit auditPlateScanning(ScanAudit scanAudit);

    EverRegisteredResponse getRegisterStatistics();
}
