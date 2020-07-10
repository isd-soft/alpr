package isd.alprserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingHistoryDTO {
    private String companyName;
    private int totalParkingSpots;
    private int leftParkingSpots;
}
