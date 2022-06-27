package com.endava.license.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class LicenseGetDto {
    private UUID id;
    private String licenseName;
    private String description;

}
