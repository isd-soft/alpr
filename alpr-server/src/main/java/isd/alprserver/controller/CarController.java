package isd.alprserver.controller;

import isd.alprserver.dto.CarDTO;
import isd.alprserver.dto.LicensePlateDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.LicenseValidationResponse;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.service.CarService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping
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
                        .status(car.getStatus().getName())
                .build()
        ).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable long id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Car> updateCar(@RequestBody Car car, @PathVariable long id)
//            throws CarAlreadyExistsException {
//        Car carById = carService.getCarById(id);
//        carById.setBrand(car.getBrand());
//        carById.setModel(car.getModel());
//        carById.setColor(car.getColor());
//        carById.setLicensePlate(car.getLicensePlate());
//        carService.update(carById);
//        return ResponseEntity.ok(carById);
//    }


    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@RequestBody CarDTO carDTO,
                                         @PathVariable(name = "id") Long id)
            throws CarAlreadyExistsException {
        carService.update(id, carDTO);
        return ResponseEntity.noContent().build();
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
    public ResponseEntity<LicenseValidationResponse> validateLicensePlate(@RequestBody List<LicensePlateDTO> licensePlate) {
        return ResponseEntity.ok(carService.getByLicensePlates(licensePlate.stream().map(LicensePlateDTO::getLicensePlate).collect(Collectors.toList())));
    }
}
