/*
 * @(#)PostInit.java
 *
 * Copyright (c) 2022 Lufthansa Cargo AG. All Rights Reserved.
 * Developed by LH Industry Solutions AS GmbH.
 *
 */
package com.moro.books.component;

import com.moro.books.entity.RatingEntity;
import com.moro.books.repository.RatingRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PostInit {

    RatingRepository ratingRepository;

    public PostInit(final RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void AfterInit() {
        RatingEntity rating = new RatingEntity();
        rating.setRating(5);
        rating.setBookId(4);
        rating.setComment("Comment");
        //ratingRepository.save(rating);
    }

}
