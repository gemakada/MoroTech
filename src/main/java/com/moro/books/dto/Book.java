package com.moro.books.dto;/*
 * @(#)Book.java
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    private Long id;

    private String title;

    private List<Author> authors;

    private double rating;

    private List<String> reviews;

}
