package com.finplanner.finplanner.model;

import lombok.Getter;

@Getter
public enum RecurrenceType {
    DAILY("Daily"),
    WEEKLY("Weekly"),
    BI_WEEKLY("BiWeekly"),
    MONTHLY("Monthly"),
    QUARTERLY("Quarterly"),
    YEARLY("Yearly");

    private final String displayName;

    RecurrenceType(String displayName) {
        this.displayName = displayName;
    }

}
