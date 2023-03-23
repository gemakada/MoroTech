/*
 * @(#)BookDTO.java
 */
package com.moro.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Data Transfer Object for Book class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    /**
     * The id
     */
    private Long id;

    /**
     * The title
     */
    private String title;

    /**
     * The authors list as {@link List<AuthorDTO>}
     */
    private List<AuthorDTO> authors;

    /**
     * The book rating
     */
    private double rating;

    /**
     * The review list as {@link List<String>}
     */
    private List<String> reviews;

}
