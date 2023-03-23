/*
* @(#)ConfigProperties.java
*/
package com.moro.books.configuration.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import javax.validation.Valid;

/**
 * Configuration properties mapped from the application.yaml (configmap).
 */
@Configuration
@ConfigurationProperties(prefix = "config")
@EnableConfigurationProperties
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProperties {

    /**
     * The gutendex service url.
     */
    @Valid
    @NonNull
    private String gutendexUrl;

    /**
     * The moro-tech book service url.
     */
    @Valid
    @NonNull
    private String moroBookServiceUrl;

}
