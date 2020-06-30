package isd.alprserver.controller;

import isd.alprserver.dto.CarDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin("*")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping("/list")
    private List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    private Car getCarById(@PathVariable long id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/{id}")
    private void deleteCar(@PathVariable("id") long id) {
        carService.delete(id);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Car> update(@RequestBody CarDTO car, @PathVariable long id)
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
    public ResponseEntity<Car> addCar(@RequestBody Car car) throws CarAlreadyExistsException {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(carService.add(car));
    }

}
