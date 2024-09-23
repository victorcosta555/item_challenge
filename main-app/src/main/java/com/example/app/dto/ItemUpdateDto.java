package com.example.app.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemUpdateDto {

    private String name;
    private BigDecimal price;
    private String description;
    private Integer quantity;
    private String category;
}
