package isd.alprserver.service;

import isd.alprserver.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAllCars();

    Car getCarById(long id);

    void add(Car car);

    Car update(Car car);

    void delete(long id);

    void add(Car car, String email);

    Optional<Car> getByLicensePlate(String licensePlate);
}
