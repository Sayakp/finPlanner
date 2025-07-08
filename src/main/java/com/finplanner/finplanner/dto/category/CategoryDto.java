package com.finplanner.finplanner.dto.category;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryDto {
    private UUID id;
    @NotNull(message = "Name required")
    private String name;
    private String description;
}
