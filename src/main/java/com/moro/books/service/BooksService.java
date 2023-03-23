/*
 * @(#)BookService.java
 */
package com.moro.books.service;

import com.moro.books.dto.BookDTO;
import com.moro.books.dto.BookSearchResultDTO;
import com.moro.books.exception.BookResultsNotAvailable;
import com.moro.books.service.facade.GutendexService;
import com.moro.books.mapper.GutendexMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements a service for
 * performing book management actions
 */
@Service
@Slf4j
public class BooksService {

    /**
     * The reference to the GutendexService
     */
    private final GutendexService gutendexService;

    /**
     * The reference to the BooksRatingService
     */
    private final BooksRatingService booksRatingService;

    /**
     * Constructor with parameters
     *
     * @param gutendexService the GutendexService
     * @param booksRatingService the BooksRatingService
     */
    public BooksService(final GutendexService gutendexService,
                        final BooksRatingService booksRatingService) {
        this.gutendexService = gutendexService;
        this.booksRatingService = booksRatingService;
    }

    /**
     * Method search's for books based on a query string and a page
     *
     * @param searchString the query string
     * @param page the requested results page
     * @return the retrieved search result as {@link BookSearchResultDTO}
     */
    @Cacheable(value = "searchCache", key = "{#searchString, #page}")
    public BookSearchResultDTO searchForBooks(String searchString, String page) {
        return gutendexService.searchGutendexBooks(searchString, page)
                .map(GutendexMapper::mapGutendexSearchResultToToDto)
                .orElseThrow(() -> new BookResultsNotAvailable("Gutendex did not provide any Book results"));
    }

    /**
     * Method gets a books with ratings based on book id
     *
     * @param bookId the book id
     * @return the retrieved book result as {@link BookDTO}
     */
    @Cacheable(value = "bookWithRatingCache", key = "{#bookId}")
    public BookDTO getBookWithRatings(String bookId) {
        return gutendexService.getGutendexBookById(bookId)
                .map(gutendexBook -> {
                    var book = GutendexMapper.mapGutendexBookToDto(gutendexBook);
                    var rating = booksRatingService.getBookRating(bookId);
                    book.setRating(rating.getRating());
                    book.setReviews(rating.getReviews());
                    return book;
                })
                .orElseThrow(() -> new BookResultsNotAvailable("Gutendex did not provide any Book results for book with id:" + bookId));
    }

    /**
     * Method gets top N rated books
     *
     * @param topN the N variable
     * @return  the retrieved list of books as {@link List<BookDTO>}
     */
    public List<BookDTO> getTopRatedBooks(int topN) {
        return gutendexService.getGutendexBookByIds(booksRatingService.getTopRatedBooks(topN)).map(gutendexBooks -> {
                    return gutendexBooks.stream().map(gutendexBook -> {
                        var book = GutendexMapper.mapGutendexBookToDto(gutendexBook);
                        var rating = booksRatingService.getBookRating(String.valueOf(gutendexBook.getId()));
                        book.setRating(rating.getRating());
                        book.setReviews(rating.getReviews());
                        return book;
                    }).collect(Collectors.toList());
                }
        ).orElseThrow(() -> new BookResultsNotAvailable("Gutendex did not provide any Book based on top rated criteria"));
    }
}
