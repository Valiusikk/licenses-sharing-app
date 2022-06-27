package com.endava.license.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
public class LicenseDto {
    private UUID id;

    @NotBlank(message = "Product description name must be between 3 and 255 characters long!")
    @Pattern(regexp = "^.{5,50}$",
            message = "License name must be between 5 and 50 characters long")
    private String licenseName;
    @NotBlank(message = "Product description name must be between 3 and 255 characters long!")
    @Pattern(regexp = "^.{5,250}$",
            message = "License description name must be between 5 and 250 characters long")
    private String description;
    @Pattern(regexp = "^\\w{5,50}$",
            message = "Username must be between 5 and 50 alphanumeric characters long")
    private String username;
    @Pattern(regexp = "^[-!$%'()*,/:;<=>?@.#&+^_`{|}~\"\\\\\\w\\s]{4,50}$",
            message = "Password must be between 4 and 50 alphanumeric characters, including special symbols")
    private String password;
    private ProductDto product;
}
