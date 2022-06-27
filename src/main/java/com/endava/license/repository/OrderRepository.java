package com.endava.license.repository;

import com.endava.license.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    List<OrderEntity> findAllByLicenseProductId(UUID id);

    Page<OrderEntity> findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(LocalDate startDate, LocalDate endDate, Pageable pageable);

    /**
     * Should return a List of all orders with the specified start date
     *
     * @param date - order start date
     * @return List with OrderEntity or empty list
     */
    List<OrderEntity> findByStartDate(LocalDate date);
    void deleteAllByLicenseId(UUID licenseId);
}
