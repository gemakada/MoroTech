package com.moro.books.dto;/*
 * @(#)BookSearchResult
 *
 * Copyright (c) 2022 Lufthansa Cargo AG. All Rights Reserved.
 * Developed by LH Industry Solutions AS GmbH.
 *
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookSearchResultDTO {

    private int count;

    private String next;

    private String previous;

    private List<BookDTO> results;

}
