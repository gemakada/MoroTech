/*
 * @(#)RestTemplateConfig.java
 */
package com.moro.books.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * The configuration for http rest client
 */
@Configuration
public class RestTemplateConfig {

    /**
     * The {@link RestTemplate} Bean
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
