package com.finplanner.finplanner.dto.budget;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class PatchBudgetDto {
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;
    @DecimalMin(value="0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private UUID categoryId;
}
