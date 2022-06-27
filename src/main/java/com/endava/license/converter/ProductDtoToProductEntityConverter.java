package com.endava.license.converter;

import com.endava.license.dto.ProductDto;
import com.endava.license.entity.ProductEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoToProductEntityConverter implements Converter<ProductDto, ProductEntity> {

    @Override
    public ProductEntity convert(ProductDto productDto) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductName(productDto.getProductName());
        productEntity.setProductDescription(productDto.getProductDescription());
        productEntity.setId(productDto.getId());
        productEntity.setStatus(productDto.getStatus());
        return productEntity;
    }
}
