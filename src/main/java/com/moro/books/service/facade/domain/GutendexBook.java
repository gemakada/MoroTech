/*
 * @(#)GutendexBook
 */
package com.moro.books.service.facade.domain;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder(value = {"id","title","authors"})
public class GutendexBook {

    private Long id;

    private String title;

    private List<GutendexAuthor> authors;
}
