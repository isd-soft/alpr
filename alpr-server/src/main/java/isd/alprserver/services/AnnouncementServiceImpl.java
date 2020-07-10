package isd.alprserver.services;

import isd.alprserver.model.Announcement;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.repositories.AnnouncementRepository;
import isd.alprserver.services.interfaces.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;

    @Override
    public void add(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    @Override
    public void remove(long id) {
        announcementRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void update(Announcement announcement) {
        Optional<Announcement> an = announcementRepository.findById(announcement.getId());
        if(an.isPresent()) {
            an.get().setDescription(announcement.getDescription());
            an.get().setPriority(announcement.getPriority());
            an.get().setTitle(announcement.getTitle());
        }


    }
    @Override
    public List<Announcement> getAll() {
        return announcementRepository.findAll();
    }

    @Override
    public Optional<Announcement> getById(long id) {
        return announcementRepository.findById(id);
    }
}
