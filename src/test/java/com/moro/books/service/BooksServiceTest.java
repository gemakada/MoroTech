/*
 * @(#)BookServiceTest.java
 */
package com.moro.books.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.books.com.test.util.TestFilesLoader;
import com.moro.books.dto.Book;
import com.moro.books.entity.RatingEntity;
import com.moro.books.exception.BookResultsNotAvailable;
import com.moro.books.repository.RatingRepository;
import com.moro.books.service.facade.GutendexService;
import com.moro.books.service.facade.domain.GutendexBook;
import com.moro.books.service.facade.domain.GutendexSearchResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
public class BooksServiceTest {

    @MockBean
    GutendexService gutendexService;

    @Autowired
    BooksRatingService booksRatingService;

    @Autowired
    BooksService booksService;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void initDatabase() throws JsonProcessingException {
        this.ratingRepository.deleteAll();
        String postEdiProduct = TestFilesLoader.getInstance().loadFileContentAsString("BooksServiceTest/BookRatingsJson.json");
        List<RatingEntity> ratingEntities = objectMapper.readValue(postEdiProduct,new TypeReference<List<RatingEntity>>(){});
        ratingRepository.saveAll(ratingEntities);
    }

    @Test
    public void searchForBooksTestCase1() throws JsonProcessingException {
        String searchResults = TestFilesLoader.getInstance().loadFileContentAsString("BooksServiceTest/GutendexSearchResultsJson.json");
        GutendexSearchResult gutendexSearchResult = objectMapper.readValue(searchResults,GutendexSearchResult.class);
        when(gutendexService.searchGutendexBooks("Juliet","1")).thenReturn(Optional.ofNullable(gutendexSearchResult));
        when(gutendexService.searchGutendexBooks("Juliet","2")).thenReturn(Optional.ofNullable(null));
        Assertions.assertEquals(booksService.searchForBooks("Juliet","1").getResults().size(),gutendexSearchResult.getResults().size());
        Assertions.assertThrows(BookResultsNotAvailable.class,()->booksService.searchForBooks("Juliet","2"));
    }

    @Test
    public void getBookWithRatingsTest() throws JsonProcessingException {
        String gutendexBookString = TestFilesLoader.getInstance().loadFileContentAsString("BooksServiceTest/GutendexBookJson.json");
        GutendexBook gutendexBook = objectMapper.readValue(gutendexBookString,GutendexBook.class);
        when(gutendexService.getGutendexBookById("2")).thenReturn(Optional.ofNullable(gutendexBook));
        when(gutendexService.getGutendexBookById("7")).thenReturn(Optional.ofNullable(null));
        Book retrievedBook = booksService.getBookWithRatings("2");
        Assertions.assertEquals(retrievedBook.getTitle(),gutendexBook.getTitle());
        Assertions.assertEquals(retrievedBook.getRating(),5);
        Assertions.assertThrows(BookResultsNotAvailable.class,()->booksService.getBookWithRatings("7"));
    }

    @Test
    public void getTopRatedBooksTest() throws JsonProcessingException {
        String gutendexBooksString = TestFilesLoader.getInstance().loadFileContentAsString("BooksServiceTest/GutendexBooksJson.json");
        GutendexSearchResult gutendexBooks = objectMapper.readValue(gutendexBooksString,GutendexSearchResult.class);
        Integer ids[] = new Integer[] { 10, 2 };
        when(gutendexService.getGutendexBookByIds(Arrays.asList(ids))).thenReturn(Optional.ofNullable(gutendexBooks.getResults()));
        List<Book> retrievedBooks = booksService.getTopRatedBooks(2);
        Assertions.assertEquals(retrievedBooks.size(),2);
        Assertions.assertEquals(retrievedBooks.size(),gutendexBooks.getResults().size());
        when(gutendexService.getGutendexBookByIds(Arrays.asList(ids))).thenReturn(Optional.ofNullable(null));
        Assertions.assertThrows(BookResultsNotAvailable.class,()->booksService.getTopRatedBooks(2));
    }






}
