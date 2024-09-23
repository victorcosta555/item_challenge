package com.example.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@ToString
public class ItemEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(length = 100, nullable = false)
    @NotNull
    private String name;

    @Column(length = 300)
    private String description;

    @Column(nullable = false)
    @NotNull
    private BigDecimal price;

    @Column(nullable = false)
    @NotNull
    private Integer quantity;

    @Column(length = 100)
    private String category;
}
