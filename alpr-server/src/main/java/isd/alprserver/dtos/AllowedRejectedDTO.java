package isd.alprserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AllowedRejectedDTO {
    private int rejectedCars;
    private int allowedCars;
}
