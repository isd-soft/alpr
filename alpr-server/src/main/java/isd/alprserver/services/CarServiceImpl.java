package isd.alprserver.services;

import isd.alprserver.dtos.CarDTO;
import isd.alprserver.model.Car;
import isd.alprserver.model.Status;
import isd.alprserver.model.User;
import isd.alprserver.model.exceptions.CarAlreadyExistsException;
import isd.alprserver.model.exceptions.CarNotFoundException;
import isd.alprserver.model.exceptions.StatusNotFoundException;
import isd.alprserver.model.exceptions.UserNotFoundException;
import isd.alprserver.model.shared.LicenseValidationResponse;
import isd.alprserver.model.statistics.CarAudit;
import isd.alprserver.model.statistics.ScanAudit;
import isd.alprserver.repositories.CarRepository;
import isd.alprserver.repositories.StatusRepository;
import isd.alprserver.repositories.UserRepository;
import isd.alprserver.services.interfaces.CarService;
import isd.alprserver.services.interfaces.ParkingHistoryService;
import isd.alprserver.services.interfaces.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final ParkingHistoryService parkingHistoryService;
    private final StatisticsService statisticsService;

    @Override
    @Transactional
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
        car = carRepository.save(car);

        saveRegistrationStatistics(car);

        return car;
    }

    @Override
    @Transactional
    public void update(Long id, CarDTO carDTO) {
        Car carById = getCarById(id);
        carById.setBrand(carDTO.getBrand());
        carById.setModel(carDTO.getModel());
        carById.setColor(carDTO.getColor());
        carById.setLicensePlate(carDTO.getLicensePlate());
    }

    @Override
    public void delete(long id) {
        carRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void add(Car car, String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User doesn't exist"));
        Status status = statusRepository.getByName("OUT").orElseThrow(() -> new StatusNotFoundException("Status not found"));
        this.carRepository.save(car);
        user.getCars().add(car);
        car.setUser(user);
        car.setStatus(status);

        saveRegistrationStatistics(car);
    }

    private void saveRegistrationStatistics(Car car) {
        statisticsService.auditCarRegistration(CarAudit.builder()
                .licensePlate(car.getLicensePlate())
                .registrationDate(new Date())
                .build());
    }

    @Override
    public Optional<Car> getByLicensePlate(String licensePlate) {
        return this.carRepository.findByLicensePlate(licensePlate);
    }

    @Override
    @Transactional
    public LicenseValidationResponse getByLicensePlates(List<String> licensePlateList) {
        Optional<Car> car = carRepository.findByLicensePlates(licensePlateList);
        if (car.isPresent()) {
            CarDTO dto = CarDTO.builder()
                    .licensePlate(car.get().getLicensePlate())
                    .brand(car.get().getBrand())
                    .color(car.get().getColor())
                    .id(car.get().getId())
                    .model(car.get().getModel())
                    .ownerCompany(car.get().getUser().getCompany().getName())
                    .ownerEmail(car.get().getUser().getEmail())
                    .ownerTelephone(car.get().getUser().getTelephoneNumber())
                    .ownerName(car.get().getUser().getFirstName() + " " + car.get().getUser().getLastName())
                    .status(car.get().getStatus().getName())
                    .build();
            if (car.get().getStatus().getName().equals("IN")) {
                addScanAuditOut(car.get());
                parkingHistoryService.getByDateAndCompanyId(getDateAsString(), car.get().getUser().getCompany().getId())
                        .incrementParkingSpots();
                car.get().setStatus(statusRepository.getByName("OUT").orElseThrow(() -> new StatusNotFoundException("Invalid status")));
                return LicenseValidationResponse.builder().car(dto).status("Left").build();
            }
            if (areAvailableSpots(car) && isOut(car)) {
                addScanAuditInAllowed(car.get());
                car.get().setStatus(statusRepository.getByName("IN").orElseThrow(() -> new StatusNotFoundException("Invalid status")));
                parkingHistoryService.getByDateAndCompanyId(getDateAsString(), car.get().getUser().getCompany().getId())
                        .decrementParkingSpots();
                return LicenseValidationResponse.builder().car(dto).status("Allowed").build();
            } else {
                addScanAuditInRejected(car.get());
                return LicenseValidationResponse.builder().car(dto).status("Forbidden").build();
            }
        } else {
            return LicenseValidationResponse.builder().car(CarDTO.builder().licensePlate(licensePlateList.get(licensePlateList.size() - 1)).build()).status("Unknown").build();
        }
    }

    private void addScanAuditOut(Car car) {
        statisticsService.addScanAudit(
                ScanAudit.builder()
                        .scanDate(new Date())
                        .status("OUT")
                        .licensePlate(car.getLicensePlate())
                        .build()
        );
    }

    private void addScanAuditInAllowed(Car car) {
        statisticsService.addScanAudit(
                ScanAudit.builder()
                        .licensePlate(car.getLicensePlate())
                        .status("IN")
                        .isAllowed(true)
                        .scanDate(new Date())
                        .build()
        );
    }

    private void addScanAuditInRejected(Car car) {
        statisticsService.addScanAudit(
                ScanAudit.builder()
                        .licensePlate(car.getLicensePlate())
                        .scanDate(new Date())
                        .isAllowed(false)
                        .status("IN")
                        .build()
        );
    }

    private String getDateAsString() {
        return new Date().toString().substring(0, 7) + " " + new Date().toString().split(" ")[5];
    }

    private boolean areAvailableSpots(Optional<Car> car) {
        return parkingHistoryService.getByDateAndCompanyId(getDateAsString(), car.get().getUser().getCompany().getId()).getNrParkingSpots() > 0;
    }

    private boolean isOut(Optional<Car> car) {
        return car.get().getStatus().getName().equals("OUT");
    }

    @Override
    public List<Car> getAllIn() {
        return getAllCars().stream()
                .filter(car -> car.getStatus().getName().equals("IN"))
                .collect(Collectors.toList());
    }
}