package isd.alprserver.model.shared;

import isd.alprserver.dtos.CarDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LicenseValidationResponse {
    private String status;
    private CarDTO car;
}
