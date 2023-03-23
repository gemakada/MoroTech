/*
 * @(#)AuthorDTO.java
 */
package com.moro.books.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Data Transfer Object for Author class
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {

    /**
     * The Author name
     */
    private String name;

    /**
     * The birth year
     */
    private int birthYear;

    /**
     * The death year
     */
    private int deathYear;
}
