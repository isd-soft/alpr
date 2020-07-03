package isd.alprserver.repository;

import isd.alprserver.model.ParkingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingHistoryRepository extends JpaRepository<ParkingHistory, Long> {
    Optional<ParkingHistory> findByDateAndCompanyId(String date, long companyId);
}
