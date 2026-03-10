package com.nutriem.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;

@Configuration
public class AnthropicConfig {

    @Value("${anthropic.api.key}")
    private String apiKey;

    @Value("${anthropic.api.url}")
    private String apiUrl;

    @Value("${anthropic.api.model}")
    private String model;

    @Value("${anthropic.api.max-tokens}")
    private Integer maxTokens;

    @Bean
    public HttpClient anthropicHttpClient() {
        return HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();
    }

    public String getApiKey()   { return apiKey; }
    public String getApiUrl()   { return apiUrl; }
    public String getModel()    { return model; }
    public Integer getMaxTokens() { return maxTokens; }
}
