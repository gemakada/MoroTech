/*
 * @(#)GutendexBook.java
 */
package com.moro.books.service.facade.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GutendexBooks {

    private List<GutendexBook> books;


}
