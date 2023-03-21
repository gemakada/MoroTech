/*
 * @(#)BooksController.java
 */
package com.moro.books.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@RestController
@Validated
public class BooksController {

    @GetMapping(value = "/v1/books/search/", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<String> searchBooks(
            @RequestParam(value = "author", required = false) @NotEmpty(message = "Author name should not be empty")  String author,
            @RequestParam(value = "page", required = false) @Min(value = 1, message = "Page number should be greater than 1")  String page) {
            return new ResponseEntity<>("OK",HttpStatus.OK);
    }

}
