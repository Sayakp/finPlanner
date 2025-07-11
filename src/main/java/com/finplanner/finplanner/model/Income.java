package com.finplanner.finplanner.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "incomes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ToString.Include
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    @ToString.Include
    private BigDecimal amount;

    @Column()
    @ToString.Include
    private String description;

    @Column(nullable = false)
    @ToString.Include
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private User user;
}
