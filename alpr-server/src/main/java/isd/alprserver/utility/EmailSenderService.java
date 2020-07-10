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
import java.util.Date;

@Component
@RequiredArgsConstructor
public class EmailSenderService {

    private final CompanyService companyService;
    private final ParkingHistoryService parkingHistoryService;
    private final MailService mailService;
    private final CarService carService;

    //"0 0 0 * * *"
    @Scheduled(cron = "*/10 * * * * *")
    @Transactional
    public void updateParkingHistory() {
//        System.out.println("here");
//        companyService.getCompanies()
//                .forEach(company -> {
//                    ParkingHistory history = ParkingHistory.builder().
//                            date(LocalDate.now())
//                            .companyId(company.getId())
//                            .nrParkingSpots(
//                                    company.getNrParkingSpots() -
//                                            (int) carService.getAllCars()
//                                    .stream()
//                                    .filter(car -> car.getStatus().getName().equals("IN"))
//                                    .filter(car -> car.getUser().getCompany().getName().equals(company.getName()))
//                                    .count()
//                            )
//                            .lastSentNotification(-1)
//                            .build();
//                    parkingHistoryService.save(history);
//                });
//        System.out.println("done");
    }

    @Scheduled(cron = "*/10 * 9-18 * * ?")
    @Transactional
    public void check() {
        LocalDate date = LocalDate.now();
        companyService.getCompanies()
                .forEach(company -> {
                    ParkingHistory history = parkingHistoryService.getByDateAndCompanyId(date, company.getId());
                    if (history.getNrParkingSpots() < 4 && history.getNrParkingSpots() != history.getLastSentNotification()) {
                        history.setLastSentNotification(history.getNrParkingSpots());
                        carService.getAllCars().stream()
                                .filter(car -> car.getUser().getCompany().getName().equals(company.getName()))
                                .filter(car -> car.getStatus().getName().equals("OUT"))
                                .forEach(car -> mailService.sendEmail(car.getUser(), history.getNrParkingSpots()));
                    }
                });
    }
}
