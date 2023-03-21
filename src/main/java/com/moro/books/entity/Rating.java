/*
 * @(#)Rating.java
 */
package com.moro.books.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table(name = "rating")
@AllArgsConstructor
@NoArgsConstructor
public class Rating {

    @Id
    @Column(value = "ID")
    int id;

    @Column(value = "BOOK_ID")
    int bookId;

    @Column(value = "RATING")
    int rating;

    @Column(value = "COMMENT")
    String comment;

}
