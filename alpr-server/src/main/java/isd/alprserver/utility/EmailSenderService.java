package isd.alprserver.utility;

import isd.alprserver.model.ParkingHistory;
import isd.alprserver.services.interfaces.CarService;
import isd.alprserver.services.interfaces.CompanyService;
import isd.alprserver.services.interfaces.MailService;
import isd.alprserver.services.interfaces.ParkingHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class EmailSenderService {

    private final CompanyService companyService;
    private final ParkingHistoryService parkingHistoryService;
    private final MailService mailService;
    private final CarService carService;

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void updateParkingHistory() {
        companyService.getAll()
                .forEach(company -> {
                    ParkingHistory history = ParkingHistory.builder().
                            date(LocalDate.now())
                            .companyId(company.getId())
                            .nrParkingSpots(
                                    company.getNrParkingSpots() -
                                            (int) carService.getAll()
                                    .stream()
                                    .filter(car -> car.getStatus().equals("IN"))
                                    .filter(car -> car.getOwnerCompany().equals(company.getName()))
                                    .count()
                            )
                            .lastSentNotification(-1)
                            .build();
                    parkingHistoryService.save(history);
                });
    }

    @Scheduled(cron = "*/30 * 8-11 * * ?")
    @Transactional
    public void check() {
        LocalDate date = LocalDate.now();
        companyService.getAll()
                .forEach(company -> {
                    ParkingHistory history = parkingHistoryService.getByDateAndCompanyId(date, company.getId());
                    if (history.getNrParkingSpots() < 4 && history.getNrParkingSpots() != history.getLastSentNotification()) {
                        history.setLastSentNotification(history.getNrParkingSpots());
                        carService.getAll().stream()
                                .filter(car -> car.getOwnerCompany().equals(company.getName()))
                                .filter(car -> car.getStatus().equals("OUT"))
                                .forEach(car -> mailService.sendNoMoreParkingSpotsEmail(car.getOwnerEmail(), history.getNrParkingSpots()));
                    }
                });
    }
}
