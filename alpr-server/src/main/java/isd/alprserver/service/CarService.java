package isd.alprserver.service;

import isd.alprserver.model.Car;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> getAllCars();

    Car getCarById(long id);

    Car add(Car car) throws CarAlreadyExistsException;

    Car update(Car car);

    void delete(long id);

    void add(Car car, String email) throws UserNotFoundException;

    Optional<Car> getByLicensePlate(String licensePlate);

    Optional<Car> getByLicensePlates(List<String> licensePlateList);
}
