package isd.alprserver.services;

import isd.alprserver.dtos.CommentDTO;
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

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
    public List<CommentDTO> getAllComments(long id) throws UserNotFoundException {
        Announcement announcement = announcementRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Invalid announcement id " + id));
        return announcement.getComments().stream()
                .sorted(getCommentComparator())
                .map(
                        comment ->
                                CommentDTO.builder()
                                        .description(comment.getDescription())
                                        .userEmail(comment.getUserEmail())
                                        .date(comment.getDate().toString())
                                        .time(comment.getTime().toString())
                                        .build()
                )
                .collect(Collectors.toList());
    }

    private void sendAnnouncements(Announcement announcement){
        userService.getAll().forEach(userDTO -> mailService.sendNotificationFromAdmin(userDTO.getEmail(), announcement.getTitle(), announcement.getDescription()) );

    }

    private Comparator<Comment> getCommentComparator() {
        return (a, b) -> {
            if (a.getDate().isAfter(b.getDate()))
                return -1;
            else if(a.getDate().isBefore(b.getDate()))
                return 1;
            else if (a.getDate().isEqual(b.getDate()) && a.getTime().isAfter(b.getTime()))
                return -1;
            else if (a.getDate().isEqual(b.getDate()) && a.getTime().isBefore(b.getTime()))
                return 1;
            return 0;
        };
    }
}
