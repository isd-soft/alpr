package isd.alprserver.dto;
import isd.alprserver.model.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompanyDTO {

    @NotNull
    private long id;

    @NotNull
    @Pattern(regexp = "[a-zA-Z]+")
    private String name;

    @NotNull
    private int nrParkingSpots;

        public Company toCompany() {
            return Company.builder()
//                    .id(id)
                    .name(name)
                    .nrParkingSpots(nrParkingSpots)
                    .build();
        }
    }
