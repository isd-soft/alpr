package isd.alprserver.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CarDTO {
    private long id;
    private String licensePlate;
    private String brand;
    private String model;
    private String color;
    private String ownerEmail;
    private String ownerName;
    private String ownerTelephone;
    private String ownerCompany;
    private String status;
}
