/*
 * @(#)GutendexAuthor.java
 */
package com.moro.books.service.facade.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Domain Object for GutendexAuthor
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"name","birth_year","death_year"})
public class GutendexAuthor {

    /**
     * The author name
     */
    private String name;

    /**
     * The birth year
     */
    @JsonProperty("birth_year")
    private int birthYear;

    /**
     * The death year
     */
    @JsonProperty("death_year")
    private int deathYear;
}
