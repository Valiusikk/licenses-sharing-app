package com.endava.license.exception.handler;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Builder
@Getter
public class ErrorMessageDto {

    private final HttpStatus httpStatus;

    private final Timestamp timestamp;

    private final String message;

    private final String code;
}


