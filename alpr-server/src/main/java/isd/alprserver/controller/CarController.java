package isd.alprserver.controller;

import isd.alprserver.dto.CarDTO;
import isd.alprserver.dto.LicensePlateDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.LicenseValidationResponse;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping()
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars().stream().map(
                car -> CarDTO.builder()
                .id(car.getId())
                .licensePlate(car.getLicensePlate())
                .brand(car.getBrand())
                .model(car.getModel())
                .color(car.getColor())
                .ownerEmail(car.getUser().getEmail())
                .ownerTelephone(car.getUser().getTelephoneNumber())
                .ownerName(car.getUser().getFirstName() + " " + car.getUser().getLastName())
                .ownerCompany(car.getUser().getCompany().getName())
                .build()
        ).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable long id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable("id") long id) {
        carService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@RequestBody Car car, @PathVariable long id)
            throws CarAlreadyExistsException {
        Car carById = carService.getCarById(id);
        carById.setBrand(car.getBrand());
        carById.setModel(car.getModel());
        carById.setColor(car.getColor());
        carById.setLicensePlate(car.getLicensePlate());
        carService.update(carById);
        return ResponseEntity.ok(carById);
    }

    @PostMapping("/add")
    public void addCar(@RequestBody CarDTO carDTO) {
        Car car = Car.builder()
                .licensePlate(carDTO.getLicensePlate())
                .brand(carDTO.getBrand())
                .model(carDTO.getModel())
                .color(carDTO.getColor())
                .build();
        this.carService.add(car, carDTO.getOwnerEmail());
    }

    @PostMapping()
    public ResponseEntity<LicenseValidationResponse> validateLicensePlate(@RequestBody List<LicensePlateDTO> licensePlate) {
        Optional<Car> car = carService.getByLicensePlates(licensePlate.stream().map(LicensePlateDTO::getLicensePlate).collect(Collectors.toList()));
        return car.map(value -> ResponseEntity.ok(LicenseValidationResponse.builder()
                .car(CarDTO.builder()
                        .id(value.getId())
                        .brand(value.getBrand())
                        .color(value.getColor())
                        .model(value.getModel())
                        .licensePlate(value.getLicensePlate())
                        .ownerCompany(value.getUser().getCompany().getName())
                        .ownerEmail(value.getUser().getEmail())
                        .ownerName(value.getUser().getFirstName() + " " + value.getUser().getLastName())
                        .ownerTelephone(value.getUser().getTelephoneNumber())
                        .build())
                .status("Allowed")
                .build()))
                .orElseGet(() -> ResponseEntity.ok(LicenseValidationResponse.builder()
                .car(CarDTO.builder().licensePlate(licensePlate.get(licensePlate.size() - 1).getLicensePlate()).build())
                .status("Forbidden")
                .build()));
    }
}
