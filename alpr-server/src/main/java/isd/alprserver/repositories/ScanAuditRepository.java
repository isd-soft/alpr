package isd.alprserver.repositories;

import isd.alprserver.model.statistics.ScanAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ScanAuditRepository extends JpaRepository<ScanAudit, Long> {
    int countAllByStatusAndIsAllowed(String status, boolean isAllowed);

    @Query(nativeQuery = true,
            value = "select * from scan_audit sa where sa.scan_date > current_date - interval '7 day' and sa.status='IN'")
    List<ScanAudit> findAllInLastWeek(Date currentDate);


}
