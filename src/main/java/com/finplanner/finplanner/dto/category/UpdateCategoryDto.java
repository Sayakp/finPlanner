package com.finplanner.finplanner.dto.category;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateCategoryDto {
    @Size(max = 100)
    private String name;
    @Size(max = 255)
    private String description;
}
