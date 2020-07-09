package isd.alprserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnnouncementDTO {
    private long id;
    private String title;
    private String description;
    private String priority;
    private LocalDate date = null;
}
