package com.endava.license.converter;

import com.endava.license.dto.RegisterRequest;
import com.endava.license.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;

public class RegisterRequestToUserEntityConverter implements Converter<RegisterRequest, UserEntity> {
    @Override
    public UserEntity convert(RegisterRequest registerRequest) {
        UserEntity userEntity = new UserEntity();

        userEntity.setPassword(registerRequest.getPassword());
        userEntity.setFirstName(registerRequest.getFirstName());
        userEntity.setLastName(registerRequest.getLastName());
        userEntity.setEmail(registerRequest.getEmail());

        return userEntity;
    }
}
