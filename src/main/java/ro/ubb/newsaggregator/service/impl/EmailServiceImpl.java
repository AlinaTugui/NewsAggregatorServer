package ro.ubb.newsaggregator.service.impl;

import org.springframework.stereotype.Component;
import ro.ubb.newsaggregator.service.EmailService;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

@Component
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendEmail(String userEmailAccount) throws MessagingException {

        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.host", "smtp-relay.sendinblue.com");
        prop.put("mail.smtp.port", "587");
        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("alinutzatugui@yahoo.com", "1234");
                //return new PasswordAuthentication("petrescue.org@gmail.com", "zcehwwlorcgsjoul");
            }
        });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("petrescue.org@gmail.com"));
        message.setRecipients(
                Message.RecipientType.TO, InternetAddress.parse(userEmailAccount));
        message.setSubject("Account created successfully");

        String msg = "Welcome to PetRescue! Be ready to adopt a new buddy! Your account has been activated!";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);

    }

}