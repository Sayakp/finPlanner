package com.finplanner.finplanner.dto.budget;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
public class BudgetDto {
    private UUID id;
    private String name;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String categoryName;
}
