package com.finplanner.finplanner.dto.category;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCategoryDto {
    @NotNull(message = "Name required")
    @Size(max = 100)
    private String name;
    @Size(max = 255)
    private String description;
}
