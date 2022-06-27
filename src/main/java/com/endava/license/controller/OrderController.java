package com.endava.license.controller;

import com.endava.license.dto.OrderDto;
import com.endava.license.dto.OrderRequest;
import com.endava.license.dto.ReadOrderDto;
import com.endava.license.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.endava.license.security.Roles.Fields.ADMIN;
import static com.endava.license.security.Roles.Fields.USER;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @GetMapping()
    @RolesAllowed(ADMIN)
    public ResponseEntity<Page<ReadOrderDto>> getOrdersByPeriod(
            @RequestParam("startDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "sort", defaultValue = "startDate") String sort,
            @RequestParam(name = "order", defaultValue = "ASC") Sort.Direction order) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findOrdersInTimePeriod(startDate, endDate, page, limit, sort, order));
    }

    @RolesAllowed({ADMIN, USER})
    @GetMapping("/{productId}")
    public ResponseEntity<List<LocalDate>> getAllOccupiedDatesForProductId(@PathVariable UUID productId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.getAllOccupiedDates(productId));
    }

    @RolesAllowed({ADMIN, USER})
    @PostMapping
    public ResponseEntity<OrderDto> applyForLicense(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.applyForLicense(orderRequest));
    }
}


