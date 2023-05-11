package ro.ubb.newsaggregator.service;

import javax.mail.MessagingException;

public interface EmailService {

    void sendEmail(String userEmailAccount) throws MessagingException;
}
