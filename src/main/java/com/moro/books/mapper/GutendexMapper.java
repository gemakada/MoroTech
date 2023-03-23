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

public class GutendexMapper {

    public static List<BookDTO> mapGutendexBooksToDto(List<GutendexBook> gutendexBooks) {
        return gutendexBooks.stream().map(gutendexBook ->
             BookDTO.builder().title(gutendexBook.getTitle())
                    .id(gutendexBook.getId())
                    .authors(mapGutendexBookAuthorToDto(gutendexBook.getAuthors()))
                    .build()
        ).collect(Collectors.toList());
    }

    public static BookDTO mapGutendexBookToDto(GutendexBook gutendexBook) {
        return BookDTO.builder().title(gutendexBook.getTitle())
                        .id(gutendexBook.getId())
                        .authors(mapGutendexBookAuthorToDto(gutendexBook.getAuthors()))
                        .build();
    }

    public static List<AuthorDTO> mapGutendexBookAuthorToDto(List<GutendexAuthor> gutendexAuthors) {
        return gutendexAuthors.stream().map(gutendexAuthor ->
                AuthorDTO.builder()
                        .birthYear(gutendexAuthor.getBirthYear())
                        .deathYear(gutendexAuthor.getDeathYear())
                        .name(gutendexAuthor.getName())
                        .build()
        ).collect(Collectors.toList());
    }

    public static BookSearchResultDTO mapGutendexSearchResultToToDto(GutendexSearchResult gutendexSearchResult) {
        return BookSearchResultDTO.builder()
                .results(mapGutendexBooksToDto(gutendexSearchResult.getResults()))
                .count(gutendexSearchResult.getCount())
                .next(gutendexSearchResult.getNext())
                .previous(gutendexSearchResult.getPrevious())
                .build();
    }


}
