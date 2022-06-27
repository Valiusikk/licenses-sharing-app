package com.endava.license.service;

public interface MailService {
    void sendEmail(String emailAddress, String subject, String text);
    void sendCredentials();
}
