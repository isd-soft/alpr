package isd.alprserver.repositories;

import isd.alprserver.model.statistics.ScanAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanAuditRepository extends JpaRepository<ScanAudit, Long> {
}
