package com.moro.books.repository;/*
 * @(#)RatingRepository.java
 */

import com.moro.books.entity.RatingEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends CrudRepository<RatingEntity, Long> {

    List<RatingEntity> findAllByBookId(int bookId);

    @Query("select  BOOK_ID from rating group by BOOK_ID order by AVG(RATING) desc  limit:topN")
    List<Integer> findTopRatedBooks(int topN);

}
