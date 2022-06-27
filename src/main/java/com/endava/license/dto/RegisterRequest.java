package com.endava.license.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class RegisterRequest {
    @Pattern(regexp = "^[a-zA-Z]{3,30}$",
            message = "First name must have only letters!")
    private String firstName;

    @Pattern(regexp = "^[a-zA-Z]{3,30}$",
            message = "Last name must have only letters!")
    private String lastName;

    @Email(regexp = "^(?=.{3,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email must follow a common standard: user@mail.com format.")
    private String email;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[><?@+'`~^%&\\*\\[\\]\\{\\}.!#|\\\\\\\"$';,:;=\\/\\(\\),\\-]).{8,64}$",
            message = "Password must contain at least one capital letter, one lower case letter and one special symbol with a length between 8 letters and 64 letters!")
    private String password;
}