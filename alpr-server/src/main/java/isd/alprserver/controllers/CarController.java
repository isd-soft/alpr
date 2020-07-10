package isd.alprserver.controllers;

import isd.alprserver.dtos.CarDTO;
import isd.alprserver.dtos.LicensePlateDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.model.shared.LicenseValidationResponse;
import isd.alprserver.services.interfaces.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cars")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carService.getAllCars()
                .stream()
                .map(this::CarToCarDTO)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public Car getCarById(@PathVariable long id) {
        return carService.getCarById(id);
    }

    @GetMapping("/company/{companyName}")
    public ResponseEntity<List<CarDTO>> getCarsByCompanyName(@PathVariable String companyName) {
        return ResponseEntity.ok(carService.getByCompanyName(companyName)
                .stream()
                .map(this::CarToCarDTO)
                .collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCar(@PathVariable("id") long id) {
        carService.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@RequestBody CarDTO carDTO,
                                         @PathVariable(name = "id") Long id) {
        carService.update(id, carDTO);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCar(@RequestBody CarDTO carDTO) throws CarAlreadyExistsException, UserNotFoundException {
        Car car = Car.builder()
                .licensePlate(carDTO.getLicensePlate())
                .brand(carDTO.getBrand())
                .model(carDTO.getModel())
                .color(carDTO.getColor())
                .photo(carDTO.getPhoto() != null ?
                        Base64.getDecoder().decode(carDTO.getPhoto().split(",")[1]) :
                        null)
                .build();
        this.carService.add(car, carDTO.getOwnerEmail());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping()
    public ResponseEntity<LicenseValidationResponse> validateLicensePlate(@RequestBody List<LicensePlateDTO> licensePlate) {
        return ResponseEntity.ok(carService.getByLicensePlates(licensePlate.stream().map(LicensePlateDTO::getLicensePlate).collect(Collectors.toList())));
    }

    @GetMapping("/in")
    public ResponseEntity<List<CarDTO>> getAllInCars() {
        return ResponseEntity.ok(
                carService.getAllIn().stream()
                        .map(this::CarToCarDTO)
                        .collect(Collectors.toList())
        );
    }

    private CarDTO CarToCarDTO(Car car) {
        return CarDTO.builder()
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
                .photo(car.getPhoto() != null ?
                        Base64.getEncoder().encodeToString(car.getPhoto()) :
                        null)
                .build();
    }
}
