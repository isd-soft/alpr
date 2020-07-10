package isd.alprserver.services.interfaces;

import isd.alprserver.model.User;

public interface MailService{
    void sendNoMoreParkingSpotsEmail(User user, int nrParkingSpots) ;

    void sendParkingNotificationEmail(String email);
}
