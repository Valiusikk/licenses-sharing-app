package com.endava.license.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class OrderRequest {
    private UUID productId;
    private UUID userId;
    private LocalDate startDate;
    private LocalDate endDate;
}
