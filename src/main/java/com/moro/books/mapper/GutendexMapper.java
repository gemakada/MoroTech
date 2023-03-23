/*
 * @(#)GutendexMapper.java
 */
package com.moro.books.mapper;

import com.moro.books.dto.AuthorDTO;
import com.moro.books.dto.BookDTO;
import com.moro.books.dto.BookSearchResultDTO;
import com.moro.books.service.facade.domain.GutendexAuthor;
import com.moro.books.service.facade.domain.GutendexBook;
import com.moro.books.service.facade.domain.GutendexSearchResult;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class Maps object between Gutendex Domain model classes
 * and DTO classes
 */
public class GutendexMapper {

    /**
     * Maps a list of GutendexBook to BookDTO list
     *
     * @param gutendexBooks the list of Gutendex books as {@link List<GutendexBook>}
     * @return the list of transformed BookDTO as {@link List<BookDTO>}
     */
    public static List<BookDTO> mapGutendexBooksToDto(List<GutendexBook> gutendexBooks) {
        return gutendexBooks.stream().map(gutendexBook ->
             BookDTO.builder().title(gutendexBook.getTitle())
                    .id(gutendexBook.getId())
                    .authors(mapGutendexBookAuthorToDto(gutendexBook.getAuthors()))
                    .build()
        ).collect(Collectors.toList());
    }

    /**
     * Maps a GutendexBook to BookDTO
     *
     * @param gutendexBook the Gutendex book as {@link GutendexBook}
     * @return the transformed BookDTO as {@link BookDTO}
     */
    public static BookDTO mapGutendexBookToDto(GutendexBook gutendexBook) {
        return BookDTO.builder().title(gutendexBook.getTitle())
                        .id(gutendexBook.getId())
                        .authors(mapGutendexBookAuthorToDto(gutendexBook.getAuthors()))
                        .build();
    }

    /**
     * Maps a list of GutendexAuthor to AuthorDTO list
     *
     * @param gutendexAuthors the list of Gutendex authors as {@link List<GutendexAuthor>}
     * @return the list of transformed AuthorDTO as {@link List<AuthorDTO>}
     */
    public static List<AuthorDTO> mapGutendexBookAuthorToDto(List<GutendexAuthor> gutendexAuthors) {
        return gutendexAuthors.stream().map(gutendexAuthor ->
                AuthorDTO.builder()
                        .birthYear(gutendexAuthor.getBirthYear())
                        .deathYear(gutendexAuthor.getDeathYear())
                        .name(gutendexAuthor.getName())
                        .build()
        ).collect(Collectors.toList());
    }

    /**
     * Maps a GutendexSearchResult to BookSearchResultDTO
     *
     * @param gutendexSearchResult the GutendexSearchResult book as {@link GutendexSearchResult}
     * @return the transformed BookSearchResultDTO as {@link BookSearchResultDTO}
     */
    public static BookSearchResultDTO mapGutendexSearchResultToToDto(GutendexSearchResult gutendexSearchResult) {
        return BookSearchResultDTO.builder()
                .results(mapGutendexBooksToDto(gutendexSearchResult.getResults()))
                .count(gutendexSearchResult.getCount())
                .next(gutendexSearchResult.getNext())
                .previous(gutendexSearchResult.getPrevious())
                .build();
    }

}
