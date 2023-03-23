/*
 * @(#)ApiError.java
 */
package com.moro.books.exception.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * DTO for custom API errors
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    /**
     * The error timestamp
     */
    private Date timestamp;

    /**
     * The status
     */
    private int status;

    /**
     * The error description
     */
    private String error;

    /**
     * The error causes as {@link List<String>}
     */
    private List<String> details;

}
