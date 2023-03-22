/*
 * @(#)GutendexService.java
 */
package com.moro.books.service.facade;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.books.configuration.properties.ConfigProperties;
import com.moro.books.service.facade.domain.GutendexBook;
import com.moro.books.service.facade.domain.GutendexSearchResult;
import com.moro.books.util.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class GutendexService {

    private final RestTemplate restTemplate;

    private final ConfigProperties configProperties;

    private final ObjectMapper objectMapper;

    public GutendexService(final RestTemplate restTemplate,
                           final ConfigProperties configProperties,
                           final ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.configProperties = configProperties;
        this.objectMapper = objectMapper;
    }

    public Optional<GutendexSearchResult> searchGutendexBooks(String title, String page) {
        if (title == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder gutendexUri = new StringBuilder();
        ResponseEntity<String> response;
        GutendexSearchResult result = null;
        gutendexUri.append(configProperties.getGutendexUrl()).append("?search=").append(title);
        if(page!=null) {
            gutendexUri.append("&page=").append(page);
        }
        try {
            HttpEntity<String> httpHeaders = new HttpEntity<String>(createHttpHeaders());
            response = restTemplate.exchange(gutendexUri.toString(), HttpMethod.GET, httpHeaders, String.class);
            log.info("Response HTTP Get for {} -> {}", gutendexUri, response.getStatusCode());
            result = objectMapper.readValue(response.getBody(), GutendexSearchResult.class);
            formNextPreviousPageUri(result);
            log.debug("Response HTTP Get for {} -> {}", gutendexUri, new JacksonUtils().toJson(result.getResults()));
        } catch (JsonProcessingException ex) {
            log.error("Response HTTP GET for {}, Could not be Parsed",gutendexUri);
        }  catch (HttpClientErrorException ex) {
            log.warn("Response HTTP Get for {} -> {}", gutendexUri, ex.getStatusCode());
        }
        return Optional.ofNullable(result);
    }

    public Optional<GutendexBook> getGutendexBookById(String bookId) {
        StringBuilder gutendexUri = new StringBuilder();
        ResponseEntity<String> response;
        GutendexBook result = null;
        gutendexUri.append(configProperties.getGutendexUrl()).append(bookId);
        try {
            HttpEntity<String> httpHeaders = new HttpEntity<String>(createHttpHeaders());
            response = restTemplate.exchange(gutendexUri.toString(), HttpMethod.GET, httpHeaders, String.class);
            log.info("Response HTTP Get for {} -> {}", gutendexUri, response.getStatusCode());
            result = objectMapper.readValue(response.getBody(), GutendexBook.class);
            log.debug("Response HTTP Get for {} -> {}", gutendexUri, new JacksonUtils().toJson(result));
        } catch (JsonProcessingException ex) {
            log.error("Response HTTP GET for {}, Could not be Parsed",gutendexUri);
        }  catch (HttpClientErrorException ex) {
            log.warn("Response HTTP Get for {} -> {}", gutendexUri, ex.getStatusCode());
        }
        return Optional.ofNullable(result);
    }

    public Optional<List<GutendexBook>> getGutendexBookByIds(List<Integer> bookIds) {
        StringBuilder gutendexUri = new StringBuilder();
        ResponseEntity<String> response;
        GutendexSearchResult result = null;
        gutendexUri.append(configProperties.getGutendexUrl()).append("?ids=");
        for( int i=0; i<bookIds.size(); i++ ) {
            if (i< bookIds.size()-1) {
                gutendexUri.append(bookIds.get(i)).append(",");
            } else {
                gutendexUri.append(bookIds.get(i));
            }
        }
        try {
            HttpEntity<String> httpHeaders = new HttpEntity<String>(createHttpHeaders());
            response = restTemplate.exchange(gutendexUri.toString(), HttpMethod.GET, httpHeaders, String.class);
            log.info("Response HTTP Get for {} -> {}", gutendexUri, response.getStatusCode());
            result = objectMapper.readValue(response.getBody(), GutendexSearchResult.class);
            log.debug("Response HTTP Get for {} -> {}", gutendexUri, new JacksonUtils().toJson(result));
        } catch (JsonProcessingException ex) {
            log.error("Response HTTP GET for {}, Could not be Parsed",gutendexUri);
        }  catch (HttpClientErrorException ex) {
            log.warn("Response HTTP Get for {} -> {}", gutendexUri, ex.getStatusCode());
        }
        return Optional.ofNullable(result.getResults());
    }

    private HttpHeaders createHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private void formNextPreviousPageUri(GutendexSearchResult result) {
        if (result.getNext()!=null) {
            result.setNext(result.getNext().replace(configProperties.getGutendexUrl(),
                    configProperties.getMoroBookServiceUrl() + "search/"));
            result.setNext(result.getNext().replace("&search",
                    "&author"));
        }
        if (result.getPrevious()!=null) {
            result.setPrevious(result.getPrevious().replace(configProperties.getGutendexUrl(),
                    configProperties.getMoroBookServiceUrl() + "search/"));
            result.setNext(result.getNext().replace("&search",
                    "&author"));
        }
    }

}
