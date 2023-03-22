/*
 * @(#)BookService.java
 */
package com.moro.books.service;

import com.moro.books.dto.Book;
import com.moro.books.dto.BookSearchResult;
import com.moro.books.exception.BookResultsNotAvailable;
import com.moro.books.service.facade.GutendexService;
import com.moro.books.util.GutendexMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BooksService {

    private final GutendexService gutendexService;

    private final BooksRatingService booksRatingService;

    public BooksService(final GutendexService gutendexService,
                        final BooksRatingService booksRatingService) {
        this.gutendexService = gutendexService;
        this.booksRatingService = booksRatingService;
    }

    @Cacheable(value = "searchCache", key = "{#author, #page}")
    public BookSearchResult searchForBooks(String author, String page) {
        return gutendexService.searchGutendexBooks(author, page)
                .map(GutendexMapper::mapGutendexSearchResultToToDto)
                .orElseThrow(() -> new BookResultsNotAvailable("Gutendex did not provide any Book results"));
    }

    @Cacheable(value = "bookCache", key = "{#bookId}")
    public Book getBookById(String bookId) {
        return gutendexService.getGutendexBookById(bookId)
                .map(GutendexMapper::mapGutendexBookToToDto)
                .orElseThrow(() -> new BookResultsNotAvailable("Gutendex did not provide any Book results for book with id:" + bookId));
    }

    @Cacheable(value = "bookWithRatingCache", key = "{#bookId}")
    public Book getBookWithRatings(String bookId) {
        return gutendexService.getGutendexBookById(bookId)
                .map(gutendexBook -> {
                    var book = GutendexMapper.mapGutendexBookToToDto(gutendexBook);
                    var rating = booksRatingService.getBookRating(bookId);
                    book.setRating(rating.getRating());
                    book.setReviews(rating.getReviews());
                    return book;
                })
                .orElseThrow(() -> new BookResultsNotAvailable("Gutendex did not provide any Book results for book with id:" + bookId));
    }

}
