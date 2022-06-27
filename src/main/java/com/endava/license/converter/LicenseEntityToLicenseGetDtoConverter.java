package com.endava.license.converter;

import com.endava.license.dto.LicenseGetDto;
import com.endava.license.entity.LicenseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LicenseEntityToLicenseGetDtoConverter implements Converter<LicenseEntity, LicenseGetDto> {

    @Override
    public LicenseGetDto convert(LicenseEntity source) {
        LicenseGetDto dto = new LicenseGetDto();
        dto.setId(source.getId());
        dto.setLicenseName(source.getLicenseName());
        dto.setDescription(source.getDescription());

        return dto;
    }
}
