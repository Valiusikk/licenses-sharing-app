package com.endava.license.repository;

import com.endava.license.entity.LicenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LicenseRepository extends JpaRepository<LicenseEntity, UUID> {
    /**
     * Should return Optional of License entity if entity with such name is stored in database
     * Otherwise an empty optional
     *
     * @param license the name of license
     * @return Optional of License entity or empty optional
     */
    Optional<LicenseEntity> findByLicenseName(String license);

    /**
     * Should return Optional of License entity if entity with such Username is stored in database
     * Otherwise an empty optional
     *
     * @param username the name of User
     * @return Optional of License entity or empty optional
     */
    Optional<LicenseEntity> findByUsername(String username);

    List<LicenseEntity> findLicensesByProductId(UUID id);
}
