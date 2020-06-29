package isd.alprserver.controller;

import isd.alprserver.model.Car;
import isd.alprserver.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin("*")
public class CarController {
    @Autowired
    private CarService carService;

    @GetMapping()
    private List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    private Car getCarById(@PathVariable long id) {
        return carService.getCarById(id);
    }

    @DeleteMapping("/{id}")
    private void deleteBook(@PathVariable("id") long id) {
        carService.delete(id);
    }

    @PutMapping("/{id}")
    private ResponseEntity<Car> update(@RequestBody Car car, @PathVariable long id) {
        Car carById = carService.getCarById(id);
        carById.setBrand(car.getBrand());
        carById.setModel(car.getModel());
        carById.setColor(car.getColor());
        carById.setLicensePlate(car.getLicensePlate());
        carService.add(carById);
        return ResponseEntity.ok(carById);
    }

    @PostMapping("/add")
    private void addCar(@RequestBody Car car) {
        carService.add(car);
    }

}
