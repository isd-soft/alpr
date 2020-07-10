package isd.alprserver.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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

    private LocalDate date;

    private int nrParkingSpots;

    private int lastSentNotification;

    @Override
    public String toString() {
        return id + " " + "company(" + companyId + ") " + date + " " + nrParkingSpots;
    }

    public void decrementParkingSpots() {
        nrParkingSpots -= 1;
    }

    public void incrementParkingSpots() {
        nrParkingSpots += 1;
    }
}
