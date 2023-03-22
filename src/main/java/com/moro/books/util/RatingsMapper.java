/*
 * @(#)RatingsMapper
 */
package com.moro.books.util;

import com.moro.books.dto.BookRating;
import com.moro.books.dto.Rating;
import com.moro.books.entity.RatingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RatingsMapper {

    public static RatingEntity mapRatingsDtoToEntity(Rating rating) {
        return RatingEntity.builder()
                .rating(rating.getRating())
                .comment(rating.getReview())
                .bookId(rating.getBookId())
                .build();
    }

    public static BookRating mapBookRatingToDto(List<RatingEntity> ratings) {
        return BookRating.builder()
                .rating(ratings.stream().mapToDouble(RatingEntity::getRating).average().orElse(0.0))
                .reviews(ratings.stream().map(RatingEntity::getComment).collect(Collectors.toList()))
                .build();
    }

}
