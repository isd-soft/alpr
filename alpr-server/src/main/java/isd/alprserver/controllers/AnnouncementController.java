package isd.alprserver.controllers;

import isd.alprserver.dtos.AnnouncementDTO;
import isd.alprserver.dtos.CommentDTO;
import isd.alprserver.model.Announcement;
import isd.alprserver.model.AnnouncementPriority;
import isd.alprserver.model.Comment;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.services.interfaces.AnnouncementService;
import isd.alprserver.services.interfaces.MailService;
import isd.alprserver.services.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/announcements")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    private AnnouncementPriority getPriorityFromString(String priority) {
        switch (priority) {
            case "YELLOW":
                return AnnouncementPriority.YELLOW;
            case "ORANGE":
                return AnnouncementPriority.ORANGE;
            default:
                return AnnouncementPriority.RED;
        }
    }


    @PostMapping()
    public ResponseEntity<?> addAnnouncement(@RequestBody AnnouncementDTO dto) {
        Announcement announcement = Announcement
                .builder()
                .date(LocalDate.now())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
        announcement.setPriority(getPriorityFromString(dto.getPriority()));
        announcementService.add(announcement);
        return ResponseEntity.ok().build();

    }

    @GetMapping()
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        return ResponseEntity.ok(
                announcementService.getAll()
                .stream()
                .map(
                        ann -> AnnouncementDTO.builder()
                                .id(ann.getId())
                                .title(ann.getTitle())
                                .description(ann.getDescription())
                                .date(ann.getDate().toString())
                                .priority(ann.getPriority().toString())
                                .build()
                        )
                        .sorted(getAnnouncementDTOComparator())
                .collect(Collectors.toList())

        );
    }

    private Comparator<AnnouncementDTO> getAnnouncementDTOComparator() {
        return (a, b) -> {
            if (LocalDate.parse(a.getDate()).isAfter(LocalDate.parse(b.getDate())))
                return -1;
            else if(LocalDate.parse(a.getDate()).isBefore(LocalDate.parse(b.getDate())))
                return 1;
            else if(a.getId() > b.getId())
                return -1;
            else
                return 0;
        };
    }

    @PutMapping()
    public ResponseEntity<?> updateAnnouncement(@RequestBody AnnouncementDTO dto) {
        Announcement announcement = Announcement.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
        announcement.setPriority(getPriorityFromString(dto.getPriority()));
        announcementService.update(announcement);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAnnouncement(@PathVariable long id) {
        announcementService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add-comment/{id}")
    public ResponseEntity<?> addCommentToAnnouncement(@PathVariable long id, @RequestBody CommentDTO commentDTO) {
        announcementService.addComment(
                id,
                Comment.builder()
                        .description(commentDTO.getDescription())
                        .userEmail(commentDTO.getUserEmail())
                        .date(LocalDate.now())
                        .time(LocalTime.now())
                .build()
        );
        return ResponseEntity.ok().build();
    }

    @GetMapping("/comments/{id}")
    public ResponseEntity<List<CommentDTO>> getAllCommentsFromAnnouncement(@PathVariable long id) throws UserNotFoundException {
        return ResponseEntity.ok(
          announcementService.getAllComments(id)
        );
    }


}
