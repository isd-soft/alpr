package isd.alprserver.service;

import isd.alprserver.model.shared.EverRegisteredResponse;
import isd.alprserver.model.statistics.CarAudit;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.model.statistics.UserAudit;

import java.util.List;

public interface StatisticsService {
    int getTotalNrAllowedCars();
    int getTotalNrRejectedCars();
    UserAudit auditUserRegistration(UserAudit userAudit);
    CarAudit auditCarRegistration(CarAudit carAudit);
    ScanAudit auditPlateScanning(ScanAudit scanAudit);
    EverRegisteredResponse getRegisterStatistics();
}
