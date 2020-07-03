package isd.alprserver.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParkingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long companyId;

    private String date;

    private int nrParkingSpots;

    private int lastSentNotification;

    @Override
    public String toString() {
        return id + " " + "company(" + companyId + ") " + date + " " + nrParkingSpots;
    }
}
