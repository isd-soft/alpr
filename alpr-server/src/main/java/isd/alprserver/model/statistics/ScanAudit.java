package isd.alprserver.model.statistics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Table(name = "scan_audit")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScanAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date scanDate;

    private String status;

    private boolean isAllowed;

    private String licensePlate;
}
