package com.booktrack.librarymanagementsystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinePolicyDTO {

    private UUID finePolicyId;

    private String category;

    private BigDecimal finePerDay;

}
