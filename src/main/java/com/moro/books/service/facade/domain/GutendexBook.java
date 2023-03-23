/*
 * @(#)GutendexBook
 */
package com.moro.books.service.facade.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Domain Object for GutendexBook
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {"id","title","authors"})
public class GutendexBook {

    /**
     * The book id
     */
    private Long id;

    /**
     * The book title
     */
    private String title;

    /**
     * The authors as {@link List<GutendexAuthor>}
     */
    private List<GutendexAuthor> authors;
}
