package com.endava.license.service.impl;

import com.endava.license.dto.OrderDto;
import com.endava.license.dto.OrderRequest;
import com.endava.license.dto.ReadOrderDto;
import com.endava.license.entity.LicenseEntity;
import com.endava.license.entity.OrderEntity;
import com.endava.license.entity.UserEntity;
import com.endava.license.exception.ExceptionType;
import com.endava.license.exception.handler.LicensesException;
import com.endava.license.repository.LicenseRepository;
import com.endava.license.repository.OrderRepository;
import com.endava.license.repository.UserRepository;
import com.endava.license.service.MailService;
import com.endava.license.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.endava.license.service.impl.MailServiceImpl.CONFIRMATION_EMAIL;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final LicenseRepository licenseRepository;
    private final ConversionService conversionService;
    private final MailService mailService;

    @Override
    public List<LocalDate> getAllOccupiedDates(UUID productId) {
        return orderRepository.findAllByLicenseProductId(productId).stream()
                .flatMap(order -> order.getStartDate().datesUntil(order.getEndDate()))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReadOrderDto> findOrdersInTimePeriod(LocalDate startDate, LocalDate endDate, int page, int limit, String sortedBy, Sort.Direction direction) {
        checkCorrectDate(startDate, endDate);
        final Pageable pageable = PageRequest.of(page, limit, direction, sortedBy);

        return orderRepository
                .findAllByStartDateGreaterThanEqualAndEndDateLessThanEqual(startDate, endDate, pageable)
                .map(order -> conversionService.convert(order, ReadOrderDto.class));
    }

    private void checkCorrectDate(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new LicensesException(ExceptionType.INVALID_DATE_IN_ORDER);
        }
    }

    @Override
    public OrderDto applyForLicense(OrderRequest request) {
        final UserEntity user = getUserEntity(request);
        final OrderEntity orderEntity = create(request);
        orderEntity.setUser(user);
        final List<UUID> allLicenses = getAllLicenseIDs(request);
        final List<UUID> allOrderForTimePeriod = getAllOrderIDs(request);
        allLicenses.removeAll(allOrderForTimePeriod);
        final UUID freeLicenseId = getFreeLicenseIds(allLicenses);
        final LicenseEntity license = licenseRepository
                .findById(freeLicenseId)
                .orElseThrow(() -> new LicensesException(ExceptionType.LICENSE_NOT_FOUND));
        orderEntity.setLicense(license);
        final OrderEntity savedOrder = orderRepository.save(orderEntity);
        mailService.sendEmail(user.getEmail(), CONFIRMATION_EMAIL, emailMessage(savedOrder));
        return conversionService.convert(savedOrder, OrderDto.class);
    }

    private UUID getFreeLicenseIds(List<UUID> allLicenses) {
        return allLicenses
                .stream()
                .findAny()
                .orElseThrow(() -> new LicensesException(ExceptionType.NO_AVAILABLE_LICENSE));
    }

    private List<UUID> getAllOrderIDs(OrderRequest request) {
        return orderRepository
                .findAll().stream()
                .filter(order -> order.getLicense().getProduct().getId().equals(request.getProductId()))
                .filter(order -> ifOverlap(order).test(order))
                .map(order -> order.getLicense().getId())
                .distinct()
                .collect(Collectors.toList());
    }

    private List<UUID> getAllLicenseIDs(OrderRequest request) {
        return licenseRepository
                .findLicensesByProductId(request.getProductId()).stream()
                .map(LicenseEntity::getId)
                .collect(Collectors.toList());
    }

    private UserEntity getUserEntity(OrderRequest request) {
        return userRepository
                .findById(request.getUserId())
                .orElseThrow(() -> new LicensesException(ExceptionType.USER_ID_NOT_FOUND));
    }

    private String emailMessage(OrderEntity order) {
        return String.format("License Book confirmation\nYou have successfully booked %s form %tD to %tD\n" +
                        "You'll receive credentials one day before the period starts",
                order.getLicense().getLicenseName(), order.getStartDate(), order.getEndDate());
    }

    private OrderEntity create(OrderRequest request) {
        final OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(UUID.randomUUID());
        orderEntity.setEndDate(request.getEndDate());
        orderEntity.setStartDate(request.getStartDate());
        return orderEntity;
    }

    private Predicate<OrderEntity> ifOverlap(OrderEntity request) {
        return order -> order.getStartDate().isAfter(request.getStartDate())
                && order.getEndDate().isBefore(request.getEndDate())
                || order.getStartDate().isBefore(request.getStartDate())
                && order.getEndDate().isAfter(request.getEndDate())
                || order.getEndDate().isAfter(request.getEndDate())
                && order.getEndDate().isAfter(request.getStartDate())
                || order.getStartDate().isBefore(request.getEndDate())
                && order.getEndDate().isAfter(request.getEndDate())
                || order.getStartDate().isEqual(request.getStartDate())
                || order.getEndDate().isEqual(request.getEndDate());
    }
}
