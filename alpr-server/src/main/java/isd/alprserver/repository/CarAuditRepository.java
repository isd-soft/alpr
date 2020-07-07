package isd.alprserver.repository;

import isd.alprserver.model.CarAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarAuditRepository extends JpaRepository<CarAudit, Long> {
}
