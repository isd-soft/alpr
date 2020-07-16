package isd.alprserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScanAuditDTO {
    private long id;
    private boolean allowed;
    private String licensePlate;
    private String scanDate;
    private String status;
}
