package com.finplanner.finplanner.dto.budget;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateBudgetDto {
    @NotNull(message = "Name required")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;
    @NotNull(message = "Amount required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    @NotNull(message = "Start date required")
    private LocalDate startDate;
    @NotNull(message = "End date required")
    private LocalDate endDate;
    private UUID categoryId;
}
