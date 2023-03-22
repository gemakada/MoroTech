/*
 * @(#)BooksRatingServiceTest.java
 */
package com.moro.books.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.books.com.test.util.TestFilesLoader;
import com.moro.books.dto.BookRating;
import com.moro.books.dto.Rating;
import com.moro.books.entity.RatingEntity;
import com.moro.books.exception.BookResultsNotAvailable;
import com.moro.books.repository.RatingRepository;
import com.moro.books.service.facade.GutendexService;
import com.moro.books.service.facade.domain.GutendexBook;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class BooksRatingServiceTest {

    @MockBean
    GutendexService gutendexService;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    BooksRatingService booksRatingService;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void initDatabase() throws JsonProcessingException {
        this.ratingRepository.deleteAll();
        String postEdiProduct = TestFilesLoader.getInstance().loadFileContentAsString("BooksRatingServiceTest/BookRatingsJson.json");
        List<RatingEntity> ratingEntities = objectMapper.readValue(postEdiProduct,new TypeReference<List<RatingEntity>>(){});
        ratingRepository.saveAll(ratingEntities);
    }

    @Test
    public void testPostBookRatingCase1() throws JsonProcessingException {
       Rating rating = Rating.builder()
               .bookId(4)
               .review("Test comment")
               .rating(4)
               .build();
        String gutendexBookString = TestFilesLoader.getInstance().loadFileContentAsString("BooksRatingServiceTest/GutendexBookJson.json");
        GutendexBook gutendexBook = objectMapper.readValue(gutendexBookString,GutendexBook.class);
        when(gutendexService.getGutendexBookById(String.valueOf(rating.getBookId()))).thenReturn(Optional.of(gutendexBook));
        Assertions.assertDoesNotThrow(()->booksRatingService.postBookRating(rating,String.valueOf(rating.getBookId())));
        Assertions.assertEquals(ratingRepository.count(), 8);
    }

    @Test
    public void testPostBookRatingCase2() throws JsonProcessingException {
        Rating rating = Rating.builder()
                .bookId(4)
                .review("Test comment")
                .rating(4)
                .build();
        String gutendexBookString = TestFilesLoader.getInstance().loadFileContentAsString("BooksRatingServiceTest/GutendexBookJson.json");
        GutendexBook gutendexBook = objectMapper.readValue(gutendexBookString,GutendexBook.class);
        when(gutendexService.getGutendexBookById(String.valueOf(rating.getBookId()))).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(BookResultsNotAvailable.class,()->booksRatingService.postBookRating(rating,String.valueOf(rating.getBookId())));
        Assertions.assertEquals(ratingRepository.count(), 7);
    }

    @Test
    public void testGetBookRating() {
        BookRating bookRating = booksRatingService.getBookRating("2");
        Assertions.assertEquals(bookRating.getRating(),5);
        Assertions.assertEquals(bookRating.getReviews().size(),4);
        bookRating = booksRatingService.getBookRating("20");
        Assertions.assertEquals(bookRating.getRating(),0.0);
        Assertions.assertEquals(bookRating.getReviews().size(),0);
    }

    @Test
    public void testGetTopRatedBooksCase1() {
        ratingRepository.deleteAll();
        Assertions.assertThrows(BookResultsNotAvailable.class,()->booksRatingService.getTopRatedBooks(1));
    }

    @Test
    public void testGetTopRatedBooksCase2() {
        Assertions.assertEquals(booksRatingService.getTopRatedBooks(2).size(),2);
    }

}
