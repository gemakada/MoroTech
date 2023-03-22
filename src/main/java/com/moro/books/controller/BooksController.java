/*
 * @(#)BooksController.java
 */
package com.moro.books.controller;

import com.moro.books.dto.Book;
import com.moro.books.dto.BookSearchResult;
import com.moro.books.dto.Rating;
import com.moro.books.service.BooksRatingService;
import com.moro.books.service.BooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@Validated
public class BooksController {

    @Autowired
    BooksService booksService;

    @Autowired
    BooksRatingService booksRatingService;
    @GetMapping(value = "/v1/books/search/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookSearchResult> searchBooks(
            @RequestParam(value = "author", required = false) @NotEmpty(message = "Author name should not be empty")  String author,
            @RequestParam(value = "page", required = false) @Min(value = 1, message = "Page number should be greater than 1")  String page) {
        return new ResponseEntity<>(booksService.searchForBooks(author, page),HttpStatus.OK);
    }

    @GetMapping(value = "/v1/books/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> getBookById(
            @PathVariable(value = "bookId", required = false) @NotEmpty(message = "Book id must not be null") @Min(value = 1, message = "Book id should be greater than 1")  String bookId) {
        return new ResponseEntity<>(booksService.getBookWithRatings(bookId),HttpStatus.OK);
    }

    @PostMapping(value = "/v1/books/rating", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Rating> postBookRating(
            @RequestBody @Valid Rating rating) {
        return new ResponseEntity<>(booksRatingService.postBookRating(rating, String.valueOf(rating.getBookId())),HttpStatus.OK);
    }
    @GetMapping(value = "/v1/books/topRated/{topN}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Book>> getTopRatedBooks(
            @PathVariable(value = "topN", required = false) @NotEmpty(message = "Top N books must not be null") @Min(value = 1, message = "Top N books must be greater than 1")  String topN) {
        return new ResponseEntity<>(booksService.getTopRatedBooks(Integer.parseInt(topN)),HttpStatus.OK);
    }

}
