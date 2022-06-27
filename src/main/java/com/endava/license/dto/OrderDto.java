package com.endava.license.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class OrderDto {
    private UUID id;
    private LocalDate startDate;
    private LocalDate endDate;
}
