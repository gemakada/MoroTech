/*
 * @(#)CustomizedResponseEntityExceptionHandler.java
 */
package com.moro.books.exception;

import com.moro.books.exception.error.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public final ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(new Date())
                .details(generateDetailsInfo(ex.getConstraintViolations()))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }
    private List<String> generateDetailsInfo(Set<ConstraintViolation<?>> constraintViolations) {
        List<String> errorList = new ArrayList<>();
        errorList.addAll(constraintViolations.stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .collect(Collectors.toList()));
        return errorList;
    }

}

