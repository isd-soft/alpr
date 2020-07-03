package isd.alprserver.service;

import isd.alprserver.model.User;
import org.springframework.mail.MailException;

public interface MailService {
    void sendEmail(User user, int nrParkingSpots) ;

    void sendNotification(String email);
}
