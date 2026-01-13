package com.booktrack.librarymanagementsystem.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinePolicyRequest {

    private String category;
    private BigDecimal finePerDay;

}
