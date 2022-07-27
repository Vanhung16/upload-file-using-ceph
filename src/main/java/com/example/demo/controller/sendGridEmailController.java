package com.example.demo.controller;

import com.example.demo.services.SendGridMailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequiredArgsConstructor

public class sendGridEmailController {

        private final SendGridMailService sendGridMailService;

        @GetMapping("/test-send-email")
        public void testSendEmail(){
            sendGridMailService.sendMail(
                    "test send email",
                    "hello sen grid hi hung",
                    Collections.singletonList("hungnv161101@gmail.com"),
                    null,
                    null
            );
        }
}
