package com.endava.license.converter;

import com.endava.license.dto.LicenseDto;
import com.endava.license.entity.LicenseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
public class LicenseDtoToLicenseEntityConverter implements Converter<LicenseDto, LicenseEntity> {
    private final ProductDtoToProductEntityConverter converter;

    @Override
    public LicenseEntity convert(LicenseDto source) {
        LicenseEntity entity = new LicenseEntity();
        entity.setLicenseName(source.getLicenseName());
        entity.setDescription(source.getDescription());
        entity.setPassword(source.getPassword());
        entity.setUsername(source.getUsername());
        entity.setProduct(converter.convert(source.getProduct()));

        return entity;
    }


}
