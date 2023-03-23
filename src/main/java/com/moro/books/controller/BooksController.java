/*
 * @(#)BooksController.java
 */
package com.moro.books.controller;

import com.moro.books.dto.BookDTO;
import com.moro.books.dto.BookSearchResultDTO;
import com.moro.books.dto.RatingDTO;
import com.moro.books.service.BooksRatingService;
import com.moro.books.service.BooksService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * This class implements RESTful Web Service operation(s) for Book objects
 */
@RestController
@Validated
public class BooksController {


    /**
    * The reference to the BooksService
    */
    private final BooksService booksService;

    /**
     * The reference to the BooksRatingService
     */
    private final BooksRatingService booksRatingService;

    /**
     * Constructor with parameters
     *
     * @param booksService the reference to BooksService
     * @param booksRatingService the reference to BooksRatingService
     */
    public BooksController(final BooksService booksService,
                           final BooksRatingService booksRatingService) {
        this.booksService = booksService;
        this.booksRatingService = booksRatingService;
    }

    /**
     * The searchBooks implements a GET endpoint for searching books
     * based on a query string and a results page
     *
     * @param searchString the query string
     * @param page the requested results page
     * @return the retrieved search result as {@link ResponseEntity<BookSearchResultDTO>}
     */
    @GetMapping(value = "/v1/books/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookSearchResultDTO> searchBooks(
            @RequestParam(value = "search", required = false) @NotEmpty(message = "Author name should not be empty")  String searchString,
            @RequestParam(value = "page", required = false) @Min(value = 1, message = "Page number should be greater than 1")  String page) {
        return new ResponseEntity<>(booksService.searchForBooks(searchString, page),HttpStatus.OK);
    }

    /**
     * The getBookById implements a GET endpoint for retrieving a book with ratings
     * based on a book id.
     *
     * @param bookId the book id
     * @return the retrieved book result as {@link ResponseEntity<BookDTO>}
     */
    @GetMapping(value = "/v1/books/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BookDTO> getBookById(
            @PathVariable(value = "bookId", required = false) @NotEmpty(message = "Book id must not be null") @Min(value = 1, message = "Book id should be greater than 1")  String bookId) {
        return new ResponseEntity<>(booksService.getBookWithRatings(bookId),HttpStatus.OK);
    }

    /**
     * The postBookRating implements a POST endpoint for adding book rating
     *
     * @param rating the received rating as {@link RatingDTO}
     * @return the saved rating as {@link ResponseEntity<RatingDTO>}
     */
    @PostMapping(value = "/v1/books/rating", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> postBookRating(
            @RequestBody @Valid RatingDTO rating) {
        return new ResponseEntity<>(booksRatingService.postBookRating(rating, String.valueOf(rating.getBookId())),HttpStatus.CREATED);
    }

    /**
     * The getTopRatedBooks implements a GET endpoint for getting top N rated books
     *
     * @param topN the N parameter
     * @return the retrieved list of books as {@link ResponseEntity<List<BookDTO>>}
     */
    @GetMapping(value = "/v1/books/topRated/{topN}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<BookDTO>> getTopRatedBooks(
            @PathVariable(value = "topN", required = true) @NotEmpty(message = "Top N books must not be null") @Min(value = 1, message = "Top N books must be greater than 1")  String topN) {
        return new ResponseEntity<>(booksService.getTopRatedBooks(Integer.parseInt(topN)),HttpStatus.OK);
    }

}
