package com.endava.license.service;

import com.endava.license.dto.LicenseDto;
import com.endava.license.dto.LicenseGetDto;
import com.endava.license.exception.handler.LicensesException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.UUID;

public interface LicenseService {

    /**
     * Should create an object of class {@link com.endava.license.entity.LicenseEntity} from provided data and inserts it into database.
     *
     * @param licenseDto that contains representation of License entity;
     * @return a newly created instance of class LicenseEntity in database converted into Data Transfer Object;
     * @throws LicensesException in case when wrong arguments were introduced.
     */
    LicenseDto save(LicenseDto licenseDto) throws LicensesException;
    List<String> deleteAllLicensesByIds(List<String> idList);
    List<LicenseDto> getAllLicensesByProductId(UUID id);
    Page<LicenseGetDto> getAllLicenses(int page, int limit, String sortedBy, Sort.Direction order);
    LicenseDto update(LicenseDto licenseDto, UUID licenseId) throws LicensesException;
    List<LicenseDto> updateMultipleLicensesByIds(List<LicenseDto> licenseDtoList);
}