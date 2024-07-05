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
            request.getHeaders().set("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiZXhwIjoxNzIwMTYyMzI4LCJpYXQiOjE3MjAxNjIwMjgsImlzcyI6IkFmZm9yZG1lZCIsImp0aSI6IjQ5OWI2ZTUyLWU0ZTktNDBhMC05OGE2LTJjNGU3ZDBmZGI0MiIsInN1YiI6ImFzcm92ZXIyMDI5QGdtYWlsLmNvbSJ9LCJjb21wYW55TmFtZSI6IlRlc3RpbmciLCJjbGllbnRJRCI6IjQ5OWI2ZTUyLWU0ZTktNDBhMC05OGE2LTJjNGU3ZDBmZGI0MiIsImNsaWVudFNlY3JldCI6InprTkdQV0tHVkJzZlVod1YiLCJvd25lck5hbWUiOiJhbmtpdCBrdW1hciIsIm93bmVyRW1haWwiOiJhc3JvdmVyMjAyOUBnbWFpbC5jb20iLCJyb2xsTm8iOiIwMTUyMDgwNzcyMiJ9.39LLyjDTK6Xa8Oj_AleXq-FCVuCcbj__7-yxC3HvJKc");
            return execution.execute(request, body);
        };
    }
}