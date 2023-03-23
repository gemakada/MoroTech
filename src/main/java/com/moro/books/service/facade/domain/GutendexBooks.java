/*
 * @(#)GutendexBook.java
 */
package com.moro.books.service.facade.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Domain Object for GutendexBooks
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GutendexBooks {

    /**
     * The books list as {@link List<GutendexBook>}
     */
    private List<GutendexBook> books;


}
