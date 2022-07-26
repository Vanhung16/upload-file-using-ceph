package com.example.demo.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(sendGridProperties.PREFIX)
@Getter
@Setter
public class sendGridProperties {
    static final String PREFIX = "send_grid";
    private String api_key;
    private String from_email;
    private String from_name;
}
