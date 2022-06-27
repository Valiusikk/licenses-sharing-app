package com.endava.license.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ExceptionType {

    EMAIL_IS_NOT_UNIQUE("Email must be unique", 400),
    INVALID_USERNAME_OR_PASSWORD("Invalid username or password", 401),
    USER_ID_NOT_FOUND("User id does not exists", 401),
    INVALID_ORDER("Sorting order passed by you is invalid", 400),
    PRODUCT_NAME_NOT_FOUND("Product name does not exists", 404),
    PRODUCT_ID_NOT_FOUND("Product id does not exists", 400),
    NO_AVAILABLE_LICENSE("License id does not exists", 400),
    LICENSE_NAME_IS_NOT_UNIQUE("License name must be unique", 400),
    USER_NAME_IS_NOT_UNIQUE("User name must be unique", 400),
    LICENSE_NOT_FOUND("License not found", 404),
    INVALID_DATE_IN_ORDER("Start date must be earlier than the end date", 400);

    private final String message;
    private final int status;
}
