/*
 * @(#)RatingEntity.java
 */
package com.moro.books.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Entity for RATING
 */
@Data
@Table(name = "rating")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RatingEntity {

    /**
    * The rating id
    */
    @Id
    @Column(value = "ID")
    int id;

    /**
     * The book id
     */
    @Column(value = "BOOK_ID")
    int bookId;

    /**
     * The rating number
     */
    @Column(value = "RATING")
    int rating;

    /**
     * The rating comment
     */
    @Column(value = "COMMENT")
    String comment;

}
