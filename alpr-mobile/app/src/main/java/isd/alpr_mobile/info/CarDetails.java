package isd.alpr_mobile.info;

import lombok.Data;

@Data
public class CarDetails {
    private String plateNumber;
    private String status;
    private String owner;
    private String company;
    private String color;
    private String brand;
}
