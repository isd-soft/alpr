package isd.alprserver.repository;

import isd.alprserver.model.ScanAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanAuditRepository extends JpaRepository<ScanAudit, Long> {
}
