package isd.alprserver.repository;

import isd.alprserver.model.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends JpaRepository<UserAudit, Long> {
}
