package com.moro.books.dto;/*
 * @(#)Author.java
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDTO {

    private String name;

    private int birthYear;

    private int deathYear;
}
