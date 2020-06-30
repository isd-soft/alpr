package isd.alprserver.service;

import isd.alprserver.model.Car;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.CarNotFoundException;
import isd.alprserver.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    @Autowired
    final private CarRepository carRepository;

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(long id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car with id = " + id + " not found"));
    }

    @Override
    public Car add(Car car) throws CarAlreadyExistsException {
        if (carRepository.existsByLicensePlate(car.getLicensePlate())) {
            throw new CarAlreadyExistsException();
        }
        car=carRepository.save(car);
        return car;
    }

    @Override
    public Car update(Car car) {
        return carRepository.save(car);
    }

    @Override
    public void delete(long id) {
        carRepository.deleteById(id);
    }
}
