package isd.alprserver.services.interfaces;

import isd.alprserver.dtos.CarDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.shared.LicenseValidationResponse;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.UserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CarService{
    List<CarDTO> getAll();

    Car getById(long id);

    void update(Long id, CarDTO carDTO);

    void delete(long id);

    void add(Car car, String email) throws UserNotFoundException, CarAlreadyExistsException;

    Optional<Car> getByLicensePlate(String licensePlate);

    List<CarDTO> getByCompanyName(String name);

    LicenseValidationResponse getByLicensePlates(List<String> licensePlateList);
    List<CarDTO> getAllIn();
}
