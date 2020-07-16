package isd.alprserver.services.interfaces;

import isd.alprserver.model.User;

public interface MailService{
    void sendNoMoreParkingSpotsEmail(String email, int nrParkingSpots) ;

    void sendParkingNotificationEmail(String email);

    void sendNotificationFromAdmin(String email, String subject, String text);
}
