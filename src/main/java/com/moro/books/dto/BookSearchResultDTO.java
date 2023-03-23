/*
 * @(#)BookSearchResultDTO.java
 */
package com.moro.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Data Transfer Object for book search result class
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchResultDTO {

    /**
     * the number of retrieved books
     */
    private int count;

    /**
     * the url to next page
     */
    private String next;

    /**
     * the url to previous page
     */
    private String previous;

    /**
     * the list of retrieved books as {@link List<BookDTO>}
     */
    private List<BookDTO> results;

}
