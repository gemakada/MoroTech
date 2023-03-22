/*
 * @(#)GutendexSerchResult.java
 */
package com.moro.books.service.facade.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GutendexSearchResult {

    private int count;

    private String next;

    private String previous;

    private List<GutendexBook> results;

}
