/*
 * @(#)RatingsMapper
 */
package com.moro.books.mapper;

import com.moro.books.dto.BookRatingDTO;
import com.moro.books.dto.RatingDTO;
import com.moro.books.entity.RatingEntity;

import java.util.List;
import java.util.stream.Collectors;

public class RatingsMapper {

    public static RatingEntity mapRatingsDtoToEntity(RatingDTO rating) {
        return RatingEntity.builder()
                .rating(rating.getRating())
                .comment(rating.getReview())
                .bookId(rating.getBookId())
                .build();
    }

    public static BookRatingDTO mapBookRatingToDto(List<RatingEntity> ratings) {
        return BookRatingDTO.builder()
                .rating(ratings.stream().mapToDouble(RatingEntity::getRating).average().orElse(0.0))
                .reviews(ratings.stream().map(RatingEntity::getComment).collect(Collectors.toList()))
                .build();
    }

}
