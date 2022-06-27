package com.endava.license.service.utils;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/auth/**";
    public static final String[] SWAGGER_URLS = {"/swagger-ui/**", "/v3/api-docs/**"};
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String CONTENT_TYPE = "application/json";
    public static final long EXPIRATION_TIME = 3600_000; //1h

}
