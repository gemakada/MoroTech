package com.moro.books.repository;/*
 * @(#)RatingRepository.java
 */

import com.moro.books.entity.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {

}
