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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/announcements")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;


    @PostMapping()
    public ResponseEntity<?> addAnnouncement(@RequestBody AnnouncementDTO dto) {
        Announcement announcement = Announcement
                .builder()
                .date(LocalDate.now())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
        switch (dto.getPriority()) {
            case "YELLOW":
                announcement.setPriority(AnnouncementPriority.YELLOW);
                break;
            case "ORANGE":
                announcement.setPriority(AnnouncementPriority.ORANGE);
                break;
            case "RED":
                announcement.setPriority(AnnouncementPriority.RED);
                break;
        }
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
                                .date(ann.getDate())
                                .priority(ann.getPriority().toString())
                                .build()
                        )
                        .sorted((announcementDTO, t1) -> {
                            if (announcementDTO.getDate().isAfter(t1.getDate()))
                                return -1;
                            else if(announcementDTO.getDate().isBefore(t1.getDate()))
                                return 1;
                            else if(announcementDTO.getId() > t1.getId())
                                return -1;
                            else
                                return 0;
                        })
                .collect(Collectors.toList())

        );
    }

    @PutMapping()
    public ResponseEntity<?> updateAnnouncement(@RequestBody AnnouncementDTO dto) {
        Announcement announcement = Announcement.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .build();
        switch (dto.getPriority()) {
            case "YELLOW":
                announcement.setPriority(AnnouncementPriority.YELLOW);
                break;
            case "ORANGE":
                announcement.setPriority(AnnouncementPriority.ORANGE);
                break;
            case "RED":
                announcement.setPriority(AnnouncementPriority.RED);
                break;
        }
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
          announcementService.getAllComments(id).stream()
                .sorted((comment, t1) -> {
                    if (comment.getDate().isAfter(t1.getDate()) && comment.getTime().isAfter(t1.getTime()))
                        return -1;
                    else if (comment.getDate().isAfter(t1.getDate()) && comment.getTime().isBefore(t1.getTime()))
                        return 1;
                    else if (comment.getDate().isEqual(t1.getDate()) && comment.getTime().isAfter(t1.getTime()))
                        return -1;
                    else if (comment.getDate().isEqual(t1.getDate()) && comment.getTime().isBefore(t1.getTime()))
                        return 1;
                    else if(comment.getDate().isBefore(t1.getDate()))
                        return 1;
                    return 0;
                })
                  .map(
                          comment ->
                                  CommentDTO.builder()
                                          .description(comment.getDescription())
                                          .userEmail(comment.getUserEmail())
                                          .date(comment.getDate())
                                          .time(comment.getTime())
                                          .build()
                  )
                .collect(Collectors.toList())
        );
    }


}
