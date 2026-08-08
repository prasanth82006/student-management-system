package com.example.studentmanagement.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendWelcomeEmail(String toEmail, String name) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("noreply@studentmanagement.com");
            message.setTo(toEmail);
            message.setSubject("Welcome to the Student Management System!");
            message.setText("Hello " + name + ",\n\nWelcome aboard! Your registration was successful.\n\nBest Regards,\nThe Admin Team");
            
            // mailSender.send(message); // Commented out to prevent dummy SMTP errors
            log.info("Welcome email simulated (not actually sent due to dummy credentials) to {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}", toEmail, e);
        }
    }
}
