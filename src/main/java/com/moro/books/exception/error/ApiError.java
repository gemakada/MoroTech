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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {

    private Date timestamp;

    private int status;

    private String error;

    private List<String> details;

}
