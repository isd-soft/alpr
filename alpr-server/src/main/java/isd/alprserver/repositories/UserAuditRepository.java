package isd.alprserver.repositories;

import isd.alprserver.model.statistics.UserAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuditRepository extends JpaRepository<UserAudit, Long> {
    @Query("SELECT COUNT(u) FROM UserAudit u")
    Long countAll();
}
