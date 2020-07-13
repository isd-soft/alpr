package isd.alprserver.services;

import isd.alprserver.dtos.AnnouncementDTO;
import isd.alprserver.model.Announcement;
import isd.alprserver.model.Comment;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.repositories.AnnouncementRepository;
import isd.alprserver.services.interfaces.AnnouncementService;
import isd.alprserver.services.interfaces.MailService;
import isd.alprserver.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementRepository announcementRepository;
    private final MailService mailService;
    private final UserService userService;

    @Override
    public void add(Announcement announcement) {
        announcementRepository.save(announcement);
        sendAnnouncements(announcement);
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

    @Override
    @Transactional
    public void addComment(long id, Comment comment) {
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if(announcement.isPresent()) {
            announcement.get().getComments().add(comment);
            comment.setAnnouncement(announcement.get());
        }
    }

    @Override
    public List<Comment> getAllComments(long id) throws UserNotFoundException {
        Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Invalid announcement id " + id));
        return announcement.getComments();
    }
    private void sendAnnouncements(Announcement announcement){
        userService.getAll().forEach(userDTO -> mailService.sendNotificationFromAdmin(userDTO.getEmail(), announcement.getTitle(), announcement.getDescription()) );

    }
}
