package com.example.demo.services;

import com.example.demo.properties.SendGridConfiguration;
import com.sendgrid.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SendGridMailServiceImpl implements SendGridMailService {

    private static final String CONTENT_TYPE_TEXT_PLAIN ="text/plain";
//    private static final String KEY_x_MOCK = "X-Mock";
    private static final String SEND_GRID_ENDPOINT_SEND_EMAIL ="mail/send";

    private final SendGridConfiguration sendGridProperties;

    @Override
    public void sendMail(String subject, String content, List<String> sendToEmails, List<String> ccEmails, List<String> bccEmails) {
        Mail mail = buildMailToSend(subject,content,sendToEmails, ccEmails, bccEmails);
        send(mail);
    }

    private void send(Mail mail){
        SendGrid sg = new SendGrid(sendGridProperties.getApiKey());
//        sg.addRequestHeader(KEY_x_MOCK, VALUE_TRUE)
        var request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint(SEND_GRID_ENDPOINT_SEND_EMAIL);
            request.setBody(mail.build());

            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());

        } catch (IOException e) {
            System.out.println("failed");
            System.out.println(e.getMessage());
        }
    }

    private Mail buildMailToSend(String subject, String contentStr, List<String> sendToEmails, List<String> ccMails, List<String> bccMails){
        Mail mail = new Mail();

        Email fromEmail = new Email();
        fromEmail.setName(sendGridProperties.getFromName());
        fromEmail.setEmail(sendGridProperties.getFromEmail());

        mail.setFrom(fromEmail);
        mail.setSubject(subject);

        Personalization personalization = new Personalization();
// set up send to email
        if(sendToEmails != null){
            for(String email : sendToEmails){
                Email to = new Email();
                to.setEmail(email);
                personalization.addTo(to);
            }
        }
        // set up cc mails
        if(ccMails != null){
            for (String email : ccMails){
                Email cc = new Email();
                cc.setEmail(email);
                personalization.addCc(cc);
            }
        }
        // set up bcc email
        if(bccMails != null) {
            for (String email : bccMails) {
                Email bcc = new Email();
                bcc.setEmail(email);
                personalization.addBcc(bcc);
            }
        }
        mail.addPersonalization(personalization);

        Content content = new Content();
        content.setType(CONTENT_TYPE_TEXT_PLAIN);
        content.setValue(contentStr);

        mail.addContent(content);
        return mail;
    }
}
