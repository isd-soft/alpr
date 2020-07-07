package isd.alprserver.repositories;

import isd.alprserver.model.statistics.ScanAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanAuditRepository extends JpaRepository<ScanAudit, Long> {
    @Query(nativeQuery = true,
            value = "select count(*) from scan_audit sa where sa.status=:status")
    int findTotalNrByStatus(String status);
}
