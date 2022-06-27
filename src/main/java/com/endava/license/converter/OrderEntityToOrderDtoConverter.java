package com.endava.license.converter;

import com.endava.license.dto.OrderDto;
import com.endava.license.entity.OrderEntity;
import org.springframework.core.convert.converter.Converter;

public class OrderEntityToOrderDtoConverter implements Converter<OrderEntity, OrderDto> {

    @Override
    public OrderDto convert(OrderEntity entity) {
        OrderDto source = new OrderDto();
        source.setId(entity.getId());
        source.setStartDate(entity.getStartDate());
        source.setEndDate(entity.getEndDate());
        return source;
    }
}
