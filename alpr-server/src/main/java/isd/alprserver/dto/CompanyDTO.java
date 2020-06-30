package isd.alprserver.dto;
import isd.alprserver.model.Company;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CompanyDTO {
    private long id;
    private String name;
    private int nrParkingSpots;
        public Company toCompany() {
            return Company.builder()
                    .id(id)
                    .name(name)
                    .nrParkingSpots(nrParkingSpots)
                    .build();
        }
    }
