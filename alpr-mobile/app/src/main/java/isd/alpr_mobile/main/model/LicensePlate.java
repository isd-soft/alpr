package isd.alpr_mobile.main.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class LicensePlate implements Serializable {
    private String characters;
}
