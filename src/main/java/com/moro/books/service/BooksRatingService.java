/*
 * @(#)BooksRatingService.java
 */
package com.moro.books.service;

import com.moro.books.dto.BookRatingDTO;
import com.moro.books.dto.RatingDTO;
import com.moro.books.exception.BookResultsNotAvailable;
import com.moro.books.repository.RatingRepository;
import com.moro.books.service.facade.GutendexService;
import com.moro.books.mapper.RatingsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * This class implements a service for
 * performing book rating management actions
 */
@Service
@Slf4j
public class BooksRatingService {

    /**
     * The reference to the GutendexService
     */
    private final GutendexService gutendexService;

    /**
     * The reference to the RatingRepository
     */
    private final RatingRepository ratingRepository;

    /**
     * Constructor with parameters
     *
     * @param gutendexService the GutendexService
     * @param ratingRepository the RatingRepository
     */
    public BooksRatingService(final GutendexService gutendexService,
                              final RatingRepository ratingRepository) {
        this.gutendexService = gutendexService;
        this.ratingRepository = ratingRepository;
    }

    /**
     * Method posts a new book rating
     *
     * @param rating the rating as {@link RatingDTO}
     * @param cacheKey the cache key for eviction
     * @return The saved rating as {@link RatingDTO}
     */
    @CacheEvict(value = "bookWithRatingCache", key = "{#cacheKey}")
    public RatingDTO postBookRating(RatingDTO rating, String cacheKey) {
        return gutendexService.getGutendexBookById(
                String.valueOf(rating.getBookId())).map(gutendexBook -> {
                ratingRepository.save(RatingsMapper.mapRatingsDtoToEntity(rating));
                log.info("Saved Rating to Database for Book with id {}", rating.getBookId());
                return rating;
        }).orElseThrow(()-> new BookResultsNotAvailable("Gutendex did not provide any Book results for book with id:" + rating.getBookId()));
    }

    /**
     * Method gets a book rating
     *
     * @param bookId the book id
     * @return The retrieved rating as {@link BookRatingDTO}
     */
    public BookRatingDTO getBookRating(String bookId) {
        return Optional.ofNullable(RatingsMapper.mapBookRatingToDto(ratingRepository.findAllByBookId(Integer.parseInt(bookId))))
                .orElse(new BookRatingDTO());
    }

    /**
     * Method gets top N rated book ids
     *
     * @param topN the N parameter
     * @return The retrieved book ids as {@link List<Integer>}
     */
    public List<Integer> getTopRatedBooks(int topN) {
        return Optional.ofNullable(ratingRepository.findTopRatedBooks(topN))
                .map(bookIds -> {
                    if (bookIds.size() == 0) {
                        return null;
                    } else {
                        return bookIds;
                    }
        }).orElseThrow(()-> new BookResultsNotAvailable("No ratings Available in database"));
    }

}
