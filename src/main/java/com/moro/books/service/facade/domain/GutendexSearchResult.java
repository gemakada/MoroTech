/*
 * @(#)GutendexSerchResult.java
 */
package com.moro.books.service.facade.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Domain Object for GutendexSearchResult
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GutendexSearchResult {

    /**
     * the number of retrieved books
     */
    private int count;

    /**
     * The next results page url
     */
    private String next;

    /**
     * The previous results page url
     */
    private String previous;

    /**
     * The books list as {@link List<GutendexBook>}
     */
    private List<GutendexBook> results;

}
