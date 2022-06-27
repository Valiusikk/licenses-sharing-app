package com.endava.license.exception.handler;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.sql.Timestamp;

@RestControllerAdvice
public class LicensesExceptionHandler {

    private final static String FAILED_VALIDATION_EXCEPTION = "FAILED_VALIDATION_EXCEPTION";
    private final static String INTERNAL_SERVER_EXCEPTION = "INTERNAL_SERVER_EXCEPTION";

    @ExceptionHandler(value = LicensesException.class)
    public ResponseEntity<ErrorMessageDto> handleException(LicensesException licensesException) {

        HttpStatus statusCode = HttpStatus.valueOf(licensesException.getExceptionType().getStatus());

        ErrorMessageDto data = ErrorMessageDto.builder()
                .httpStatus(statusCode)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .message(licensesException.getMessage())
                .code(licensesException.getExceptionType().name())
                .build();

        return ResponseEntity
                .status(statusCode)
                .body(data);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessageDto> handleException(MethodArgumentNotValidException validException) {

        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        String message = validException.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .findFirst()
                .orElse(validException.getMessage());

        ErrorMessageDto data = ErrorMessageDto.builder()
                .httpStatus(statusCode)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .message(message)
                .code(FAILED_VALIDATION_EXCEPTION)
                .build();

        return ResponseEntity
                .status(statusCode)
                .body(data);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorMessageDto> handleException(ConstraintViolationException validException) {

        HttpStatus statusCode = HttpStatus.BAD_REQUEST;
        String message = validException.getMessage();

        ErrorMessageDto data = ErrorMessageDto.builder()
                .httpStatus(statusCode)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .message(message)
                .code(FAILED_VALIDATION_EXCEPTION)
                .build();

        return ResponseEntity
                .status(statusCode)
                .body(data);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorMessageDto> handleException(Exception exception) {

        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorMessageDto data = ErrorMessageDto.builder()
                .httpStatus(statusCode)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .message(exception.getMessage())
                .code(INTERNAL_SERVER_EXCEPTION)
                .build();

        return ResponseEntity
                .status(statusCode)
                .body(data);
    }
}
