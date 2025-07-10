package com.finplanner.finplanner.dto.expense;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class ExpenseDto {
    private UUID id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private String categoryName;
}
