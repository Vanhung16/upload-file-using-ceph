package com.example.demo.services;

import java.util.List;

public interface SendGridMailService {
    void sendMail(String subject, String content, List<String> sendToEmails, List<String> ccEmails, List<String> bccEmails);
}
