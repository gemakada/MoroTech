/*
 * @(#)BookRatingDTO.java
 */
package com.moro.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Data Transfer Object for Book Rating class
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookRatingDTO {

    /**
     * The rating
     */
    private double rating;

    /**
     * The reviews as {@link List<String>}
     */
    private List<String> reviews;

}
