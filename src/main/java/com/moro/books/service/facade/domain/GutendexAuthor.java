/*
 * @(#)GutendexAuthor.java
 */
package com.moro.books.service.facade.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder(value = {"name","birth_year","death_year"})
public class GutendexAuthor {

    private String name;

    @JsonProperty("birth_year")
    private int birthYear;

    @JsonProperty("death_year")
    private int deathYear;
}
