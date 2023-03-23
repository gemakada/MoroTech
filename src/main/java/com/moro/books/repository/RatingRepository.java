/*
 * @(#)RatingRepository.java
 */
package com.moro.books.repository;

import com.moro.books.entity.RatingEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This interface defines the CRUD methods for the JDBC Template
 * regarding the RATING entities
 */
@Repository
public interface RatingRepository extends CrudRepository<RatingEntity, Long> {

    /**
     * Retrieves all rating entities based on book id
     *
     * @param bookId the book id
     * @return the retrieved rating entities as {@link List<RatingEntity>}
     */
    List<RatingEntity> findAllByBookId(int bookId);

    /**
     * Retrieves top N book ids based on average rating
     *
     * @param topN the N parameter
     * @return the retrieved book ids as {@link List<Integer>}
     */
    @Query("select  BOOK_ID from rating group by BOOK_ID order by AVG(RATING) desc  limit:topN")
    List<Integer> findTopRatedBooks(int topN);

}
