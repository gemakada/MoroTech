/*
 * @(#)RatingDTO.java
 */
package com.moro.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * The Data Transfer Object for book search result class
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {

    /**
     * the book id
     */
    @Min(value = 1, message = "The book id to be rated should be greater than 1")
    private int bookId;

    /**
     * the book rating
     */
    @Min(value = 1, message = "Book rating should be between 1 and 5")
    @Max(value = 5, message = "Book rating should be between 1 and 5")
    private int rating;

    /**
     * the review as text
     */
    @NotEmpty
    private String review;

}
