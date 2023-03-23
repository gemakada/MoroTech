/*
 * @(#)CustomizedResponseEntityExceptionHandler.java
 */
package com.moro.books.exception;

import com.moro.books.exception.error.ApiError;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * This class implements methods for creating customized responses in service exception cases.
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler {

    /**
     * Handle ConstraintViolationException Exception
     *
     * @param ex the Exception
     * @return the {@link ResponseEntity<>} for BAD_REQUEST
     */
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

    /**
     * Handle MethodArgumentNotValidException Exception
     *
     * @param ex the Exception
     * @return the {@link ResponseEntity<>} for BAD_REQUEST
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public final ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(new Date())
                .details(generateDetailsFromFieldErrors(ex.getBindingResult().getFieldErrors()))
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();
        return new ResponseEntity<>(apiError,HttpStatus.BAD_REQUEST);
    }

    /**
     * Handle BookResultsNotAvailable Exception
     *
     * @param ex the Exception
     * @return the {@link ResponseEntity<>} for NOT_FOUND
     */
    @ExceptionHandler(value = {BookResultsNotAvailable.class})
    public final ResponseEntity<Object> handleBookResultsNotAvailable(BookResultsNotAvailable ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(new Date())
                .details(List.of(ex.getMessage()))
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .status(HttpStatus.NOT_FOUND.value())
                .build();
        return new ResponseEntity<>(apiError,HttpStatus.NOT_FOUND);
    }

    /**
     * Generates the custom API 400 error message details in case of constraints violations
     *
     * @param constraintViolations the constraints violations as {@link Set<ConstraintViolation<>}
     * @return the error details as {@link List<String>}
     */
    private List<String> generateDetailsInfo(Set<ConstraintViolation<?>> constraintViolations) {
        List<String> errorList = new ArrayList<>();
        errorList.addAll(constraintViolations.stream()
                .map(constraintViolation -> constraintViolation.getMessage())
                .collect(Collectors.toList()));
        return errorList;
    }

    /**
     * Generates the custom API 400 error message details in case of methos arguments not valid
     *
     * @param fieldErrors the constraints violations as {@link List<FieldError<>}
     * @return the error details as {@link List<String>}
     */
    private List<String> generateDetailsFromFieldErrors(List<FieldError> fieldErrors) {
        List<String> errorList = new ArrayList<>();
        errorList.addAll(fieldErrors.stream()
                .map(fieldError -> new StringBuilder()
                        .append(fieldError.getField())
                        .append(" :")
                        .append(fieldError.getDefaultMessage()).toString()).toList());
        return errorList;
    }

}

