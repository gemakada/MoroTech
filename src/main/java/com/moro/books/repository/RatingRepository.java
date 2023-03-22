package com.moro.books.repository;/*
 * @(#)RatingRepository.java
 */

import com.moro.books.entity.RatingEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends CrudRepository<RatingEntity, Long> {

    List<RatingEntity> findAllByBookId(int bookId);

}
