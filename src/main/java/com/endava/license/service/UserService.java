package com.endava.license.service;

import com.endava.license.dto.LoginRequest;
import com.endava.license.dto.LoginResponse;
import com.endava.license.dto.RegisterRequest;
import com.endava.license.dto.UserDto;

public interface UserService {
    /**
     * Should return UserDto if user with such email does not exist
     *
     * Otherwise, it is thrown an exception
     *
     * @param request RegisterRequest which contains credentials of a user, such as first name, last name, email and password
     * @return LoginResponse - response we get after successful registration and auto login
     */
    LoginResponse registerUser(RegisterRequest request);
    /**
     * Should return UserDto if user with such (username)email is registered
     *
     * Otherwise, throws an exception
     *
     *
     * @param request LoginRequest that contains email and password of user, that is going to log in
     * @return LoginResponse - response we get after successful login
     */
    LoginResponse login(LoginRequest request);
}

