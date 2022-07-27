package com.example.demo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(SendGridConfiguration.PREFIX)
@Getter
@Setter
public class SendGridConfiguration {
    static final String PREFIX = "sendmail";
    private String apiKey;
    private String fromEmail;
    private String fromName;
}
