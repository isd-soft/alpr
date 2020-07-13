package isd.alprserver.services;

import isd.alprserver.model.User;
import isd.alprserver.services.interfaces.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Override
    @Transactional(readOnly = true)
    public void sendNoMoreParkingSpotsEmail(User user, int nrParkingSpots) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setFrom("alprs003@gmail.com");
        message.setSubject("Parking Notification");
        if(nrParkingSpots == 0)
            message.setText("Sorry, but there are no more free parking spots for your company");
        else
            message.setText("There is/are " + nrParkingSpots + " free parking spots left");
        javaMailSender.send(message);
    }

    @Override
    public void sendParkingNotificationEmail(String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("alprs003@gmail.com");
        message.setSubject("Parking Notification");
        message.setText("Your presence is requested outside as you may have created a difficult parking situation.");
        javaMailSender.send(message);
    }


    @Override
    public void sendNotificationFromAdmin(String email, String subject, String text)  throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("alprs003@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}
