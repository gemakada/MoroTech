/*
 * @(#)BooksRatingControllerTest.java
 */
package com.moro.books.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.moro.books.com.test.util.TestFilesLoader;
import com.moro.books.dto.BookDTO;
import com.moro.books.dto.BookSearchResultDTO;
import com.moro.books.dto.RatingDTO;
import com.moro.books.exception.BookResultsNotAvailable;
import com.moro.books.repository.RatingRepository;
import com.moro.books.service.BooksRatingService;
import com.moro.books.service.BooksService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BooksController.class)
public class BooksRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    BooksRatingService mockBooksRatingService;

    @MockBean
    RatingRepository ratingRepository;

    @MockBean
    BooksService mockBooksService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testSearchBooksController() throws Exception {
        String retrieveSearchResults = TestFilesLoader.getInstance().loadFileContentAsString("BooksRatingControllerTest/BooksSearchResultJson.json");
        BookSearchResultDTO retrievedSearchResultsDTO = objectMapper.readValue(retrieveSearchResults, BookSearchResultDTO.class);
        when(mockBooksService.searchForBooks("Juliet","1")).thenReturn(retrievedSearchResultsDTO);
        when(mockBooksService.searchForBooks("Juliet",null)).thenReturn(retrievedSearchResultsDTO);
        when(mockBooksService.searchForBooks("Juliet","2")).thenThrow(new BookResultsNotAvailable("Gutendex did not provide any Book results"));
        this.mockMvc.perform(get("/v1/books/?search=Juliet&page=1"))
                .andExpect(status().isOk())
                .andExpect(content().json(retrieveSearchResults));
        this.mockMvc.perform(get("/v1/books/?search=Juliet&page=2"))
                .andExpect(status().isNotFound());
        this.mockMvc.perform(get("/v1/books/?search=Juliet&page=-1"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/v1/books/?page=-1"))
                .andExpect(status().isBadRequest());
        this.mockMvc.perform(get("/v1/books/?search=Juliet"))
                .andExpect(status().isOk())
                .andExpect(content().json(retrieveSearchResults));
    }

    @Test
    public void testGetBookWithRatingsController() throws Exception {
        String retrievedBookResult = TestFilesLoader.getInstance().loadFileContentAsString("BooksRatingControllerTest/BookJson.json");
        BookDTO retrievedBookResultDTO = objectMapper.readValue(retrievedBookResult, BookDTO.class);
        when(mockBooksService.getBookWithRatings("2")).thenReturn(retrievedBookResultDTO);
        when(mockBooksService.getBookWithRatings("5")).thenThrow(new BookResultsNotAvailable("Gutendex did not provide any Book results"));
        this.mockMvc.perform(get("/v1/books/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(retrievedBookResult));
        this.mockMvc.perform(get("/v1/books/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTopRatedBooksController() throws Exception {
        String retrievedBooksResult = TestFilesLoader.getInstance().loadFileContentAsString("BooksRatingControllerTest/BooksJson.json");
        List<BookDTO> retrievedBooksResultDTO = objectMapper.readValue(retrievedBooksResult, new TypeReference<List<BookDTO>>() {});
        when(mockBooksService.getTopRatedBooks(2)).thenReturn(retrievedBooksResultDTO);
        when(mockBooksService.getTopRatedBooks(3)).thenThrow(new BookResultsNotAvailable("Gutendex did not provide any Book results"));
        this.mockMvc.perform(get("/v1/books/topRated/2"))
                .andExpect(status().isOk())
                .andExpect(content().json(retrievedBooksResult));
        this.mockMvc.perform(get("/v1/books/topRated/3"))
                .andExpect(status().isNotFound());
        this.mockMvc.perform(get("/v1/books/topRated/-3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostRatingController() throws Exception {
        String ratingPost = TestFilesLoader.getInstance().loadFileContentAsString("BooksRatingControllerTest/BookRatingJson.json");
        String ratingPost2 = TestFilesLoader.getInstance().loadFileContentAsString("BooksRatingControllerTest/BookRatingJson2.json");
        RatingDTO ratingPostDTO = objectMapper.readValue(ratingPost, RatingDTO.class);
        RatingDTO ratingPostDTO2 = objectMapper.readValue(ratingPost2, RatingDTO.class);

        when(mockBooksRatingService.postBookRating(ratingPostDTO,String.valueOf(ratingPostDTO.getBookId()))).thenReturn(ratingPostDTO);
        when(mockBooksRatingService.postBookRating(ratingPostDTO2,String.valueOf(ratingPostDTO2.getBookId()))).thenThrow(new BookResultsNotAvailable("Gutendex did not provide any Book results"));
        this.mockMvc.perform(post("/v1/books/rating").header("Content-Type","Application/json").content(ratingPost))
                .andExpect(status().isCreated())
                .andExpect(content().json(ratingPost));
        this.mockMvc.perform(post("/v1/books/rating").header("Content-Type","Application/json").content(ratingPost2))
                .andExpect(status().isNotFound());
        this.mockMvc.perform(post("/v1/books/rating").header("Content-Type","Application/json").content(""))
                .andExpect(status().isBadRequest());
    }

}
