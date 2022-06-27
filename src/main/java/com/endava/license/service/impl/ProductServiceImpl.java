package com.endava.license.service.impl;

import com.endava.license.dto.ProductDto;
import com.endava.license.entity.ProductEntity;
import com.endava.license.repository.ProductRepository;
import com.endava.license.service.ProductService;
import com.endava.license.service.utils.LicenseConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableScheduling
public class ProductServiceImpl implements ProductService {

    private final ConversionService conversionService;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public Page<ProductDto> getProductByName(String productName, int page, int limit, String sortedBy, Sort.Direction order) {
        final Pageable pageable = PageRequest.of(page, limit, order, sortedBy);
        return productRepository
                .findByProductNameIgnoreCaseContaining(productName, pageable)
                .map(product -> conversionService.convert(product, ProductDto.class));
    }

    @Override
    public ProductDto addProduct(ProductDto product) {
        ProductEntity productEntity = conversionService.convert(product, ProductEntity.class);
        productEntity.setCreatedAt(Instant.now());
        productEntity.setStatus(LicenseConstants.NEW_PRODUCT_STATUS);
        ProductEntity savedProductEntity = productRepository.save(productEntity);
        log.info("Save product with ID: [{}]", savedProductEntity.getId());
        return conversionService.convert(savedProductEntity, ProductDto.class);
    }

    @Override
    @Transactional
    public Page<ProductDto> getAllProducts(int page, int limit, String sortedBy, Sort.Direction order) {
        final Pageable pageable = PageRequest.of(page, limit, order, sortedBy);

        return productRepository
                .findAll(pageable)
                .map(product -> conversionService.convert(product, ProductDto.class));
    }

    @Scheduled(cron = "0 0 0 * * *")
    protected void calcStatus() {
        final List<ProductEntity> productEntityList = productRepository.findAll().stream()
                .filter(product -> product.getStatus().equals(LicenseConstants.NEW_PRODUCT_STATUS))
                .filter(this::IsProductOlderThanAMonth)
                .peek(product -> product.setStatus(LicenseConstants.NOT_NEW_PRODUCT_STATUS))
                .collect(Collectors.toList());
        productRepository.saveAll(productEntityList);

    }

    private boolean IsProductOlderThanAMonth(ProductEntity product) {
        ZoneId currentTimeZoneId = Calendar.getInstance().getTimeZone().toZoneId();
        Instant currentTime = Instant.now().atZone(currentTimeZoneId).minus(30, ChronoUnit.DAYS).toInstant();
        return currentTime.compareTo(product.getCreatedAt()) > 0;
    }
}
