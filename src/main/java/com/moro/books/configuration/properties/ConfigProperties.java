/*
* @(#)ConfigProperties.java
*/
package com.moro.books.configuration.properties;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.validation.Valid;

@Configuration
@ConfigurationProperties(prefix = "config")
@EnableConfigurationProperties
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConfigProperties {

    @Valid
    @NonNull
    private String gutendexUrl;

    @Valid
    @NonNull
    private String moroBookServiceUrl;

}
