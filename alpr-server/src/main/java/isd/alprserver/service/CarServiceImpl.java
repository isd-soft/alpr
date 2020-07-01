package isd.alprserver.service;

import isd.alprserver.model.Car;
import isd.alprserver.model.Status;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.CarNotFoundException;
import isd.alprserver.model.exceptions.StatusNotFoundException;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.repository.CarRepository;
import isd.alprserver.repository.StatusRepository;
import isd.alprserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;

    @Override
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    public Car getCarById(long id) {
        return carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car with id = " + id + " not found"));
    }

    @Override
    public Car add(Car car) throws CarAlreadyExistsException {
        if (carRepository.findByLicensePlate(car.getLicensePlate()).isPresent()) {
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

    @Override
    @Transactional
    public void add(Car car, String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
        Status status = statusRepository.getByName("OUT").orElseThrow(()->new StatusNotFoundException("Status not found"));
        this.carRepository.save(car);
        user.getCars().add(car);
        car.setUser(user);
        car.setStatus(status);

    }

    @Override
    public Optional<Car> getByLicensePlate(String licensePlate) {
        return this.carRepository.findByLicensePlate(licensePlate);
    }
}
