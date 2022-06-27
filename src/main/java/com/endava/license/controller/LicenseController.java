package com.endava.license.controller;

import com.endava.license.dto.LicenseDto;
import com.endava.license.dto.LicenseGetDto;
import com.endava.license.service.LicenseService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

import static com.endava.license.security.Roles.Fields.ADMIN;

@RestController
@Validated
@RequestMapping("/licenses")
@RequiredArgsConstructor
public class LicenseController {

    private final LicenseService licenseService;

    @PostMapping
    @RolesAllowed(ADMIN)
    public ResponseEntity<LicenseDto> addLicense(
        @Parameter(required = true, schema = @Schema(implementation = LicenseDto.class))
        @Valid @RequestBody LicenseDto licenseDto
    ) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(licenseService.save(licenseDto));
    }

    @DeleteMapping
    @RolesAllowed(ADMIN)
    public ResponseEntity<List<String>> deleteLicenses(@RequestParam("id") List<String> idList) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(licenseService.deleteAllLicensesByIds(idList));
    }

    @GetMapping("/{id}")
    @RolesAllowed(ADMIN)
    public ResponseEntity<List<LicenseDto>> getAllLicensesByProductId(@PathVariable UUID id) {
        return ResponseEntity.ok(licenseService.getAllLicensesByProductId(id));
    }

    @GetMapping
    @RolesAllowed(ADMIN)
    public ResponseEntity<Page<LicenseGetDto>> getAllLicenses(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "sort", defaultValue = "licenseName") String sort,
            @RequestParam(name = "order", defaultValue = "ASC") Sort.Direction order
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(licenseService.getAllLicenses(page, limit, sort, order));
    }

    @RolesAllowed(ADMIN)
    @PutMapping("/{licenseId}")
    public ResponseEntity<LicenseDto> updateLicense(@RequestBody @Valid LicenseDto licenseDto, @PathVariable UUID licenseId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(licenseService.update(licenseDto, licenseId));
    }

    @PutMapping
    @RolesAllowed(ADMIN)
    public ResponseEntity<List<LicenseDto>> updateLicenses(@RequestBody @Valid List<LicenseDto> licenseDtoList) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(licenseService.updateMultipleLicensesByIds(licenseDtoList));
    }
}
