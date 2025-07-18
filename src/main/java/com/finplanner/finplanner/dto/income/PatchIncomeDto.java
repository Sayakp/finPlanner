package com.finplanner.finplanner.dto.income;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PatchIncomeDto {
    private BigDecimal amount;
    private String description;
    private LocalDate date;
}
