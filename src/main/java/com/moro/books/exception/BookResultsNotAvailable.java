/*
 * @(#)BookResultsNotAvailable.java
 */
package com.moro.books.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * This class implements specific service exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class BookResultsNotAvailable  extends RuntimeException {

    /**
     * Instantiates a new NOT_FOUND error exception.
     *
     * @param message the message
     */
    public BookResultsNotAvailable(final String message) {
        super(message);
    }
}
