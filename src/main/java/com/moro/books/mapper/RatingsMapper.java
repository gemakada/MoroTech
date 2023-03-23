/*
 * @(#)RatingsMapper.java
 */
package com.moro.books.mapper;

import com.moro.books.dto.BookRatingDTO;
import com.moro.books.dto.RatingDTO;
import com.moro.books.entity.RatingEntity;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class Maps object between DTO classes
 * and Rating Entity
 */
public class RatingsMapper {

    /**
     * Maps a RatingDTO to RatingEntity
     *
     * @param rating the RatingDTO rating as {@link RatingDTO}
     * @return the transformed RatingEntity as {@link RatingEntity}
     */
    public static RatingEntity mapRatingsDtoToEntity(RatingDTO rating) {
        return RatingEntity.builder()
                .rating(rating.getRating())
                .comment(rating.getReview())
                .bookId(rating.getBookId())
                .build();
    }

    /**
     * Maps a list of RatingEntities to BookRatingDTO
     *
     * @param ratings the list of entities as {@link List<RatingEntity>}
     * @return the transformed BookRatingDTO as {@link List<BookRatingDTO>}
     */
    public static BookRatingDTO mapBookRatingToDto(List<RatingEntity> ratings) {
        return BookRatingDTO.builder()
                .rating(ratings.stream().mapToDouble(RatingEntity::getRating).average().orElse(0.0))
                .reviews(ratings.stream().map(RatingEntity::getComment).collect(Collectors.toList()))
                .build();
    }

}
