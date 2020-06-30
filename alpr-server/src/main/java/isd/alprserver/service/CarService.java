package isd.alprserver.service;

import isd.alprserver.model.Car;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;

import java.util.List;

public interface CarService {
    List<Car> getAllCars();

    Car getCarById(long id);

    Car add(Car car) throws CarAlreadyExistsException;

    Car update(Car car);

    void delete(long id);
}
