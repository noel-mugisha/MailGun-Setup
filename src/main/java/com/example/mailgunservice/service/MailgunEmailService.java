package com.example.mailgunservice.service;

import com.example.mailgunservice.config.MailgunConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Base64;

@Service
@Slf4j
public class MailgunEmailService {
    private final WebClient webClient;
    private final MailgunConfig config;
    private final String messagesEndpoint;
    private final String authHeader;

    public MailgunEmailService(MailgunConfig config) {
        this.config = config;

        // Prepare Basic Auth header (Username: api, Password: API_KEY)
        String authString = "api:" + config.getApiKey();
        this.authHeader = "Basic " + Base64.getEncoder().encodeToString(authString.getBytes());

        // Build the specific Mailgun API endpoint path for sending messages
        this.messagesEndpoint = config.getApiBaseUrl() + config.getSendingDomain() + "/messages";

        // Initialize WebClient
        this.webClient = WebClient.builder()
                .baseUrl(config.getApiBaseUrl())
                .defaultHeader("Authorization", this.authHeader)
                .build();


    }

    /**
     * Sends a simple text-only email using the Mailgun HTTP API.
     * @param to The recipient's email address.
     * @param subject The subject of the email.
     * @param body The plain text body of the email.
     */
    public void sendEmail(String to, String subject, String body) {
        // Mailgun requires data to be sent as application/x-www-form-urlencoded
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("from", config.getFromEmail());
        formData.add("to", to);
        formData.add("subject", subject);
        formData.add("text", body); // Use 'html' for HTML content

        try {
            log.info("Attempting to send email to {} via Mailgun.", to);
            webClient.post()
                    .uri(config.getSendingDomain() + "/messages")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(formData))
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse.bodyToMono(String.class)
                                    .map(errorBody -> new RuntimeException("Mailgun API Error: " + errorBody)))
                    .bodyToMono(String.class)
                    .block(); // Block for synchronous call. Consider making this method 'Mono<String>' and @Async if needed.

            log.info("Email successfully sent to {}.", to);
        } catch (Exception e) {
            log.error("Failed to send email to {}. Error: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }
}
