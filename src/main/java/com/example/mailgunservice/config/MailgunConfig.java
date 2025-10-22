package com.example.mailgunservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "mailgun")
@Data
public class MailgunConfig {
    private String apiKey;
    private String apiBaseUrl;
    private String sendingDomain;
    private String fromEmail;
}
