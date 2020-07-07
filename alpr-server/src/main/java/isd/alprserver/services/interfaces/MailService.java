package isd.alprserver.services.interfaces;

import isd.alprserver.model.User;

public interface MailService {
    void sendEmail(User user, int nrParkingSpots) ;

    void sendNotification(String email);
}
