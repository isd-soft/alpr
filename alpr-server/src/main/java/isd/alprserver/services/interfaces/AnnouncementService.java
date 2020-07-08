package isd.alprserver.services.interfaces;

import isd.alprserver.model.Announcement;

import java.util.List;
import java.util.Optional;

public interface AnnouncementService {
    void add(Announcement announcement);
    void remove(long id);
    void update(Announcement announcement);
    List<Announcement> getAll();
    Optional<Announcement> getById(long id);
}
