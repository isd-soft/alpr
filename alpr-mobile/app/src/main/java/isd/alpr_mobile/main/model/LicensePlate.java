package isd.alpr_mobile.main.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
public class LicensePlate {
    private String licensePlate;

    public LicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
