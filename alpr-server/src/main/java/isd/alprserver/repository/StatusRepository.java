package isd.alprserver.repository;


import isd.alprserver.model.Status;
import isd.alprserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository  extends JpaRepository<Status, Integer> {
    Optional<Status> getByName(String name);

}



