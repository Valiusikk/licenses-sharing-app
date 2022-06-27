package com.endava.license.repository;

import com.endava.license.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,UUID> {
    /**
     * Should return Optional of Product entity if entity with such name is stored in database
     * Otherwise an empty optional
     *
     * @param productName the name of product, can't be longer than 50 symbols
     * @return Optional of Product entity or empty optional
     */
    Page<ProductEntity> findByProductNameIgnoreCaseContaining(String productName, Pageable page);
}
