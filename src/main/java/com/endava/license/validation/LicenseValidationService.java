package com.endava.license.validation;

import com.endava.license.dto.LicenseDto;
import com.endava.license.entity.LicenseEntity;
import com.endava.license.exception.handler.LicensesException;
import com.endava.license.repository.LicenseRepository;
import com.endava.license.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.Consumer;

import static com.endava.license.exception.ExceptionType.*;

@Component
@RequiredArgsConstructor
public final class LicenseValidationService {

    private final ProductRepository productRepository;
    private final LicenseRepository licenseRepository;

    public void checkProductExistence(LicenseDto licenseDto) {
        productRepository.findById(licenseDto.getProduct().getId())
                .orElseThrow(() -> new LicensesException(PRODUCT_ID_NOT_FOUND));
    }

    public void checkIfNameIsPresent(LicenseDto licenseDto) {
        licenseRepository.findByLicenseName(licenseDto.getLicenseName())
                .ifPresent(licenseEntity -> {
                    throw new LicensesException(LICENSE_NAME_IS_NOT_UNIQUE);
                });
    }

    public void checkIfUserNameIsPresent(LicenseDto licenseDto) {
        licenseRepository.findByUsername(licenseDto.getUsername())
                .ifPresent(licenseEntity -> {
                    throw new LicensesException(USER_NAME_IS_NOT_UNIQUE);
                });
    }

    public void validateLicenseName(LicenseDto licenseDto, UUID licenseId) {
        licenseRepository.findByLicenseName(licenseDto.getLicenseName())
                .ifPresent(checkIfLicenseNameIsNotUsed(licenseId));
    }

    public void validateLicenseUsername(LicenseDto licenseDto, UUID currentLicenseId) {
        licenseRepository.findByUsername(licenseDto.getUsername())
                .ifPresent(checkIfLicenseUsernameIsNotUsed(currentLicenseId));
    }

    private Consumer<LicenseEntity> checkIfLicenseUsernameIsNotUsed(UUID currentLicenseId) {
        return licenseEntity -> {
            if (!licenseEntity.getId().equals(currentLicenseId)) {
                throw new LicensesException(USER_NAME_IS_NOT_UNIQUE);
            }
        };
    }

    private Consumer<LicenseEntity> checkIfLicenseNameIsNotUsed(UUID currentLicenseId) {
        return licenseEntity -> {
            if (!licenseEntity.getId().equals(currentLicenseId)) {
                throw new LicensesException(LICENSE_NAME_IS_NOT_UNIQUE);
            }
        };
    }
}
