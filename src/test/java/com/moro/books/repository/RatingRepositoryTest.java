package com.moro.books.repository;/*
 * @(#)RatingRepositoryTest.java
 *
 * Copyright (c) 2022 Lufthansa Cargo AG. All Rights Reserved.
 * Developed by LH Industry Solutions AS GmbH.
 *
 */

import com.moro.books.entity.RatingEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Streamable;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("test")
public class RatingRepositoryTest {

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    public void initDataBase() {
        ratingRepository.deleteAll();
        RatingEntity rating = new RatingEntity();
        rating.setRating(5);
        rating.setBookId(4);
        rating.setComment("Comment");
        ratingRepository.save(rating);
    }

    @Test
    public void TestShouldRetrieveAllDataAirline() {
        List<RatingEntity> retrievedEntities = Streamable
                .of(ratingRepository.findAll())
                .toList();
        Assertions.assertEquals(retrievedEntities.size(), 1);
    }
}
