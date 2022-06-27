package com.endava.license.security;

import lombok.experimental.FieldNameConstants;

@FieldNameConstants(onlyExplicitlyIncluded = true)
public enum Roles {
    @FieldNameConstants.Include
    ADMIN,
    @FieldNameConstants.Include
    USER;
}
