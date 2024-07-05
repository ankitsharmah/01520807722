package com.example.Avergaecalcluator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Add an interceptor to include the Authorization header
        restTemplate.setInterceptors(Collections.singletonList(authInterceptor()));

        return restTemplate;
    }

    private ClientHttpRequestInterceptor authInterceptor() {
        return (request, body, execution) -> {
            // Add the authorization header (e.g., Bearer token)
            request.getHeaders().set("Authorization", "Bearer your-static-token");
            return execution.execute(request, body);
        };
    }
}