package com.endava.license.service.impl;

import com.endava.license.dto.LicenseDto;
import com.endava.license.dto.LicenseGetDto;
import com.endava.license.entity.LicenseEntity;
import com.endava.license.exception.handler.LicensesException;
import com.endava.license.repository.LicenseRepository;
import com.endava.license.repository.OrderRepository;
import com.endava.license.service.LicenseService;
import com.endava.license.validation.LicenseValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.endava.license.exception.ExceptionType.NO_AVAILABLE_LICENSE;

@Slf4j
@Service
@RequiredArgsConstructor
public class LicenseServiceImpl implements LicenseService {

    private final LicenseRepository licenseRepository;
    private final OrderRepository orderRepository;
    private final ConversionService conversionService;
    private final LicenseValidationService licenseValidationService;

    @Override
    @Transactional
    public LicenseDto save(LicenseDto licenseDto) throws LicensesException {
        licenseValidationService.checkProductExistence(licenseDto);
        licenseValidationService.checkIfNameIsPresent(licenseDto);
        licenseValidationService.checkIfUserNameIsPresent(licenseDto);

        LicenseEntity newLicense = conversionService.convert(licenseDto, LicenseEntity.class);
        LicenseEntity savedLicense = licenseRepository.save(newLicense);

        log.info("Save license with ID: [{}]", savedLicense.getId());
        return conversionService.convert(savedLicense, LicenseDto.class);
    }

    @Override
    @Transactional
    public LicenseDto update(LicenseDto licenseDto, UUID licenseId) throws LicensesException {
        licenseValidationService.validateLicenseUsername(licenseDto, licenseId);
        licenseValidationService.validateLicenseName(licenseDto, licenseId);

        LicenseEntity licenseEntity = licenseRepository
                .findById(licenseId)
                .orElseThrow(() -> new LicensesException(NO_AVAILABLE_LICENSE));
        licenseEntity = updateLicenseEntity(licenseEntity, licenseDto);
        LicenseEntity newLicenseEntity = licenseRepository.save(licenseEntity);

        return conversionService.convert(newLicenseEntity, LicenseDto.class);
    }

    private LicenseEntity updateLicenseEntity(LicenseEntity licenseEntity, LicenseDto newValues) {
        licenseEntity.setLicenseName(newValues.getLicenseName());
        licenseEntity.setPassword(newValues.getPassword());
        licenseEntity.setUsername(newValues.getUsername());
        licenseEntity.setDescription(newValues.getDescription());
        return licenseEntity;
    }

    @Transactional
    public List<String> deleteAllLicensesByIds(List<String> idList) {
        log.info("Deleting licenses with ids: " + idList.toString());
        idList.stream()
                .map(UUID::fromString)
                .forEach(id -> {
                    orderRepository.deleteAllByLicenseId(id);
                    licenseRepository.deleteById(id);
                });
        return idList;
    }

    @Override
    @Transactional
    public List<LicenseDto> getAllLicensesByProductId(UUID id) {
        return licenseRepository.findAll().stream()
                .filter(license -> license.getProduct().getId().equals(id))
                .map(license -> conversionService.convert(license, LicenseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<LicenseDto> updateMultipleLicensesByIds(List<LicenseDto> licenseDtoList) {
        licenseDtoList.forEach(dto -> update(dto, dto.getId()));
        return licenseDtoList;
    }

    @Override
    public Page<LicenseGetDto> getAllLicenses(int page, int limit, String sortedBy, Sort.Direction order) {
        final Pageable pageable = PageRequest.of(page, limit, order, sortedBy);

        return licenseRepository
                .findAll(pageable)
                .map(license -> conversionService.convert(license, LicenseGetDto.class));
    }

}
