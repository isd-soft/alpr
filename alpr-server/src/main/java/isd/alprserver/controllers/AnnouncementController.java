package isd.alprserver.controllers;

import isd.alprserver.dtos.AnnouncementDTO;
import isd.alprserver.model.Announcement;
import isd.alprserver.model.AnnouncementPriority;
import isd.alprserver.services.interfaces.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/announcements")
@CrossOrigin("*")
@RequiredArgsConstructor
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @PostMapping()
    public void addAnnouncement(@RequestBody AnnouncementDTO dto) {
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
    }

    @GetMapping()
    public ResponseEntity<List<AnnouncementDTO>> getAllAnnouncements() {
        return ResponseEntity.ok(
                announcementService.getAll()
                .stream()
                .map(
                        ann -> AnnouncementDTO.builder()
                        .title(ann.getTitle())
                        .description(ann.getDescription())
                        .date(ann.getDate())
                        .priority(ann.getPriority().toString())
                        .build()
                        )
                .collect(Collectors.toList())
        );
    }

    private void sendAnnouncements(@RequestBody AnnouncementDTO dto){



    }
}
