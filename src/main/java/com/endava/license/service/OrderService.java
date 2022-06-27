package com.endava.license.service;

import com.endava.license.dto.OrderDto;
import com.endava.license.dto.OrderRequest;
import com.endava.license.dto.ReadOrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.List;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    /**
     * Should return List of dates, when licenses for selected product(by id) is occupied
     * if product with such id exist in database
     * Otherwise empty list
     *
     * @param productId uuid that is identifier for peoduct
     * @return List of dates or empty list
     */
    List<LocalDate> getAllOccupiedDates(UUID productId);

    Page<ReadOrderDto> findOrdersInTimePeriod(LocalDate startDate, LocalDate endDate, int page, int limit, String sortedBy, Sort.Direction order);

    OrderDto applyForLicense(OrderRequest request);
}
