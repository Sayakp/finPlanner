package com.finplanner.finplanner.dto.expense;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class CreateExpenseDto {
    @NotNull(message = "Amount needed")
    private BigDecimal amount;
    private String description;
    @NotNull(message = "Date required")
    private LocalDate date;
    @NotNull
    private UUID categoryId;
}
