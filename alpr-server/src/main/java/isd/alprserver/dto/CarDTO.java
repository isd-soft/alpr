package isd.alprserver.dto;

import isd.alprserver.model.Car;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CarDTO {
    private String licensePlate;
    private String brand;
    private String model;
    private String color;

    public Car toCar() {
        return Car.builder()
                .licensePlate(licensePlate)
                .brand(brand)
                .model(model)
                .color(color)
                .build();
    }
}


