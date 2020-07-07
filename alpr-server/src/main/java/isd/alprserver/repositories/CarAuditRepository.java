package isd.alprserver.repositories;

import isd.alprserver.model.statistics.CarAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarAuditRepository extends JpaRepository<CarAudit, Long> {
}
