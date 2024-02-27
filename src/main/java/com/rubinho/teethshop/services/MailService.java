package com.rubinho.teethshop.services;

import com.rubinho.teethshop.exceptions.AppException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
@Service
public class MailService {
    @Value("${spring.mail.username}")
    private String fromEmail;

    private final JavaMailSender mailSender;

    @Async
    public void sendEmailHtml(String toEmail, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setFrom(new InternetAddress(fromEmail));
            message.setRecipients(MimeMessage.RecipientType.TO, toEmail);

            String htmlTemplate = readFile("src/main/resources/templates/code_template.html");
            String htmlContent = htmlTemplate.replace("{code}", text);

            message.setContent(htmlContent, "text/html; charset=utf-8");
            message.setSubject(subject);

            mailSender.send(message);


        } catch (Exception ex) {
            throw new AppException("Couldn't send an email", HttpStatus.INTERNAL_SERVER_ERROR);
        }


//        SimpleMailMessage message = new SimpleMailMessage();
//
//        message.setFrom(fromEmail);
//        message.setSubject(subject);
//        message.setTo(toEmail);
//        message.setText(text);
//
//        mailSender.send(message);
    }

    public void sendMyself(String subject, String text) {
        sendEmailHtml(fromEmail, subject, text);
    }

    public String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path, StandardCharsets.UTF_8);
    }


}