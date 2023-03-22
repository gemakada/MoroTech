/*
 * @(#)GutendexMapper.java

 */
package com.moro.books.util;

import com.moro.books.dto.Author;
import com.moro.books.dto.Book;
import com.moro.books.dto.BookSearchResult;
import com.moro.books.service.facade.domain.GutendexAuthor;
import com.moro.books.service.facade.domain.GutendexBook;
import com.moro.books.service.facade.domain.GutendexSearchResult;

import java.util.List;
import java.util.stream.Collectors;

public class GutendexMapper {

    public static List<Book> mapGutendexBooksToToDto(List<GutendexBook> gutendexBooks) {
        return gutendexBooks.stream().map(gutendexBook ->
             Book.builder().title(gutendexBook.getTitle())
                    .id(gutendexBook.getId())
                    .authors(mapGutendexBookAuthorToDto(gutendexBook.getAuthors()))
                    .build()
        ).collect(Collectors.toList());
    }

    public static Book mapGutendexBookToToDto(GutendexBook gutendexBook) {
        return Book.builder().title(gutendexBook.getTitle())
                        .id(gutendexBook.getId())
                        .authors(mapGutendexBookAuthorToDto(gutendexBook.getAuthors()))
                        .build();
    }

    public static List<Author> mapGutendexBookAuthorToDto(List<GutendexAuthor> gutendexAuthors) {
        return gutendexAuthors.stream().map(gutendexAuthor ->
                Author.builder()
                        .birthYear(gutendexAuthor.getBirthYear())
                        .deathYear(gutendexAuthor.getDeathYear())
                        .name(gutendexAuthor.getName())
                        .build()
        ).collect(Collectors.toList());
    }

    public static BookSearchResult mapGutendexSearchResultToToDto(GutendexSearchResult gutendexSearchResult) {
        return BookSearchResult.builder()
                .results(mapGutendexBooksToToDto(gutendexSearchResult.getResults()))
                .count(gutendexSearchResult.getCount())
                .next(gutendexSearchResult.getNext())
                .previous(gutendexSearchResult.getPrevious())
                .build();
    }


}
