package isd.alprserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String userEmail;
    private String description;
    private LocalDate date;
    private LocalTime time;
}
