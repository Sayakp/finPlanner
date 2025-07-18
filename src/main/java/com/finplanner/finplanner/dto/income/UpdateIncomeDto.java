package com.finplanner.finplanner.dto.income;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateIncomeDto {
    @NotNull
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    private String description;
    @NotNull(message = "Date is required")
    private LocalDate date;
}
