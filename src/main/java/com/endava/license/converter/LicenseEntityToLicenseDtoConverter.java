package com.endava.license.converter;


import com.endava.license.dto.LicenseDto;
import com.endava.license.entity.LicenseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LicenseEntityToLicenseDtoConverter implements Converter<LicenseEntity, LicenseDto> {

    private final ProductEntityToProductDtoConverter converter;

    @Override
    public LicenseDto convert(LicenseEntity source) {
        LicenseDto dto = new LicenseDto();
        dto.setId(source.getId());
        dto.setLicenseName(source.getLicenseName());
        dto.setDescription(source.getDescription());
        dto.setPassword(source.getPassword());
        dto.setUsername(source.getUsername());
        dto.setProduct(converter.convert(source.getProduct()));

        return dto;
    }
}
