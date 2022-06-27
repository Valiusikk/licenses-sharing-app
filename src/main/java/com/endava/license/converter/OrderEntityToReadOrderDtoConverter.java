package com.endava.license.converter;

import com.endava.license.dto.OrderDto;
import com.endava.license.dto.ReadOrderDto;
import com.endava.license.entity.LicenseEntity;
import com.endava.license.entity.OrderEntity;
import org.springframework.core.convert.converter.Converter;

import java.util.stream.Collectors;

public class OrderEntityToReadOrderDtoConverter implements Converter<OrderEntity, ReadOrderDto> {

    @Override
    public ReadOrderDto convert(OrderEntity entity) {
        ReadOrderDto source = new ReadOrderDto();
        source.setId(entity.getId());
        source.setStartDate(entity.getStartDate());
        source.setEndDate(entity.getEndDate());
        source.setLicenseName(entity.getLicense().getLicenseName());
        source.setUsername(entity.getLicense().getUsername());
        return source;
    }
}
