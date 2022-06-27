package com.endava.license.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Data
public class ProductDto {
    private UUID id;

    @Pattern(regexp = "^.{3,50}$",
            message = "Product name must be between 3 and 50 characters long!")
    @NotBlank(message = "Product description name must be between 3 and 255 characters long!")
    private String productName;

    @NotBlank(message = "Product description name must be between 3 and 255 characters long!")
    @Pattern(regexp = "^.{3,255}$",
            message = "Product description name must be between 3 and 255 characters long!")
    private String productDescription;

    private String status;
    private int licensesCount;
}
