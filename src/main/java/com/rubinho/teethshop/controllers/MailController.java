package com.rubinho.teethshop.controllers;

import com.rubinho.teethshop.dto.MailDto;
import com.rubinho.teethshop.dto.OrderDto;
import com.rubinho.teethshop.model.OrderState;
import com.rubinho.teethshop.services.MailService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/v1/")
public class MailController {
    private final MailService mailService;

    @PostMapping("/mail/faq")
    public ResponseEntity<String> faq(MailDto mailDto) {
        mailService.sendFAQ(mailDto.getSubject(), mailDto.getText());
        return ResponseEntity.ok("Email sent");
    }
}
