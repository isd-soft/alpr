package isd.alprserver.controller;

import isd.alprserver.dto.CarDTO;
import isd.alprserver.dto.LicensePlateDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<Car> updateCar(@RequestBody Car car, @PathVariable long id) {
        Car carById = carService.getCarById(id);
        carById.setBrand(car.getBrand());
        carById.setModel(car.getModel());
        carById.setColor(car.getColor());
        carById.setLicensePlate(car.getLicensePlate());
        carService.add(carById);
        return ResponseEntity.ok(carById);
    }

    @PostMapping("/add")
    public void addCar(@RequestBody CarDTO carDTO) throws UserNotFoundException {
        Car car = Car.builder()
                .licensePlate(carDTO.getLicensePlate())
                .brand(carDTO.getBrand())
                .model(carDTO.getModel())
                .color(carDTO.getColor())
                .build();
        this.carService.add(car, carDTO.getOwnerEmail());
    }

    @PostMapping()
    public CarDTO validateLicensePlate(@RequestBody LicensePlateDTO licensePlate) {
        Car car = carService.getByLicensePlate(licensePlate.getLicensePlate()).orElse(null);
        return CarDTO.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .color(car.getColor())
                .model(car.getModel())
                .licensePlate(car.getLicensePlate())
                .ownerCompany(car.getUser().getCompany().getName())
                .ownerEmail(car.getUser().getEmail())
                .ownerName(car.getUser().getFirstName() + " " + car.getUser().getLastName())
                .ownerTelephone(car.getUser().getTelephoneNumber())
                .build();
    }

}
