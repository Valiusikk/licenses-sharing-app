package com.endava.license.converter;

import com.endava.license.dto.ProductDto;
import com.endava.license.entity.ProductEntity;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductEntityToProductDtoConverter implements Converter<ProductEntity, ProductDto> {
    @Override
    public ProductDto convert(ProductEntity productEntity) {
        ProductDto productDto = new ProductDto();
        productDto.setId(productEntity.getId());
        productDto.setProductName(productEntity.getProductName());
        productDto.setProductDescription(productEntity.getProductDescription());
        productDto.setId(productEntity.getId());
        productDto.setStatus(productEntity.getStatus());
        productDto.setLicensesCount(productEntity.getLicense() != null ? productEntity.getLicense().size() : 0);
        return productDto;
    }
}
