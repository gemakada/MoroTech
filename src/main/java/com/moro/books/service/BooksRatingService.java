/*
 * @(#)BooksRatingService.java
 */
package com.moro.books.service;

import com.moro.books.dto.BookRating;
import com.moro.books.dto.Rating;
import com.moro.books.exception.BookResultsNotAvailable;
import com.moro.books.repository.RatingRepository;
import com.moro.books.service.facade.GutendexService;
import com.moro.books.util.RatingsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class BooksRatingService {

    private final GutendexService gutendexService;

    private final RatingRepository ratingRepository;

    public BooksRatingService(final GutendexService gutendexService,
                              final RatingRepository ratingRepository) {
        this.gutendexService = gutendexService;
        this.ratingRepository = ratingRepository;
    }

    public Rating postBookRating(Rating rating) {
        return gutendexService.getGutendexBookById(
                String.valueOf(rating.getBookId())).map(gutendexBook -> {
                ratingRepository.save(RatingsMapper.mapRatingsDtoToEntity(rating));
                log.info("Saved Rating to Database for Book with id {}", rating.getBookId());
                return rating;
        }).orElseThrow(()-> new BookResultsNotAvailable("Gutendex did not provide any Book results for book with id:" + rating.getBookId()));
    }

    public BookRating getBookRating(String bookId) {
        return Optional.ofNullable(RatingsMapper.mapBookRatingToDto(ratingRepository.findAllByBookId(Integer.parseInt(bookId))))
                .orElse(new BookRating());
    }

}
