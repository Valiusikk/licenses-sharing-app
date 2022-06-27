package com.endava.license.dto;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Data
public class LoginRequest {

    @Email(regexp = "^(?=.{3,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$",
            message = "Email should comply with a general standard: user@mail.com format.")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[><?@+'`~^%&\\*\\[\\]\\{\\}.!#|\\\\\\\"$';,:;=\\/\\(\\),\\-]).{8,64}$",
            message = "Password should contain at least one capital letter and one special symbol, to have 8=<length=<64")
    private String password;

}
