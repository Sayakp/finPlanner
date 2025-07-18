package com.finplanner.finplanner.dto.income;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class IncomeDto {
    private UUID id;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
}
