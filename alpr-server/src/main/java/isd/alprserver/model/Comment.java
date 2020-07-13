package isd.alprserver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "comments")
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String userEmail;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Announcement announcement;

    private LocalDate date;

    private LocalTime time;
}
