package com.endava.license.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ReadOrderDto {
    private UUID id;
    private String licenseName;
    private String username;
    private LocalDate startDate;
    private LocalDate endDate;
}
