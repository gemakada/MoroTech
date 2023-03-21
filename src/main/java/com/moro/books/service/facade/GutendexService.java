/*
 * @(#)GutendexService.java
 */
package com.moro.books.service.facade;

import com.moro.books.service.facade.domain.GutendexBook;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GutendexService {

    RestTemplate restTemplate;

    public GutendexService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GutendexBook searchGutendexBooks(String author, String page) {
        return new GutendexBook();
    }

}
