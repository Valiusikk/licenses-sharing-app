package com.endava.license.exception.handler;

import com.endava.license.exception.ExceptionType;
import lombok.Getter;

@Getter
public class LicensesException extends RuntimeException {

    private final ExceptionType exceptionType;

    public LicensesException(ExceptionType exceptionType) {

        super(exceptionType.getMessage());

        this.exceptionType = exceptionType;
    }
}
