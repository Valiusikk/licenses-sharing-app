package com.endava.license.service.impl;

import com.endava.license.entity.OrderEntity;
import com.endava.license.repository.OrderRepository;
import com.endava.license.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
@EnableAsync
@EnableScheduling
public class MailServiceImpl implements MailService {

    private final JavaMailSender javaMailSender;
    private final OrderRepository orderRepository;
    private static final String CREDENTIALS_EMAIL_SUBJECT = "Your Credentials for ";
    public static final String CONFIRMATION_EMAIL = "Confirmation email";

    @Override
    @Async
    public void sendEmail(String emailAddress, String subject, String text) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        javaMailSender.send(mailMessage);
    }

    @Override
    @Async
    @Scheduled(cron = "0 0 0 * * *")
    public void sendCredentials() {
        orderRepository.findByStartDate(LocalDate.now().plusDays(1))
                .forEach(orderEntity -> sendEmail(orderEntity.getUser().getEmail(),
                        CREDENTIALS_EMAIL_SUBJECT + orderEntity.getLicense().getProduct().getProductName(),
                        createCredentialsEmailSubject(orderEntity)));
    }

    private String createCredentialsEmailSubject(OrderEntity order) {
        String user = order.getUser().getFirstName() + " " + order.getUser().getLastName() + ",";
        String productName = order.getLicense().getProduct().getProductName();
        String userName = order.getLicense().getUsername();
        String password = order.getLicense().getPassword();
        LocalDate startDate = order.getStartDate();
        LocalDate endDate = order.getEndDate();

        return String.format("Dear %s please see credentials for %s\n" +
                        "License username: %s\n" +
                        "License password: %s\n" +
                        "Please do not share this information with other persons and do not use it outside the selected period %tD - %tD.",
                user, productName, userName, password, startDate, endDate);
    }
}
