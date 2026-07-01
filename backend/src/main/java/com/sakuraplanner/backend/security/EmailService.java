package com.sakuraplanner.backend.security;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Verifica il tuo account SakuraPlanner");
            
            // In a real application, this URL should come from properties or environment
            String verificationUrl = "http://localhost:8080/api/v1/auth/verify?token=" + token;
            
            message.setText("Benvenuto in SakuraPlanner!\n\n" +
                    "Per favore verifica il tuo account cliccando sul link sottostante:\n" +
                    verificationUrl + "\n\n" +
                    "Il link scadrà tra 24 ore.");
            
            mailSender.send(message);
            log.info("Email di verifica inviata a {}", toEmail);
        } catch (Exception e) {
            log.error("Errore durante l'invio dell'email di verifica a {}", toEmail, e);
        }
    }
}
