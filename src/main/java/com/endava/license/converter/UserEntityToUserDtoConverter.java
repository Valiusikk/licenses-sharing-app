package com.endava.license.converter;

import com.endava.license.dto.UserDto;
import com.endava.license.entity.UserEntity;
import org.springframework.core.convert.converter.Converter;

public class UserEntityToUserDtoConverter implements Converter<UserEntity, UserDto> {

    @Override
    public UserDto convert(UserEntity entity) {
        UserDto dto = new UserDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setId(entity.getId());
        return dto;
    }
}