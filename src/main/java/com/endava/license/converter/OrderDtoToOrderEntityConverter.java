package com.endava.license.converter;

import com.endava.license.dto.OrderDto;
import com.endava.license.entity.OrderEntity;
import org.springframework.core.convert.converter.Converter;

public class OrderDtoToOrderEntityConverter implements Converter<OrderDto, OrderEntity> {

    @Override
    public OrderEntity convert(OrderDto source) {
        OrderEntity entity = new OrderEntity();
        entity.setId(source.getId());
        entity.setStartDate(source.getStartDate());
        entity.setEndDate(source.getEndDate());
        return entity;
    }
}
