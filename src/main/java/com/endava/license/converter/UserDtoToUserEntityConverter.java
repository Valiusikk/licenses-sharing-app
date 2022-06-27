package com.endava.license.converter;

import com.endava.license.dto.UserDto;
import com.endava.license.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;

public class UserDtoToUserEntityConverter implements Converter<UserDto, UserEntity> {

    @Override
    public UserEntity convert(UserDto dto) {
        UserEntity user = new UserEntity();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setId(dto.getId());
        return user;
    }
}
