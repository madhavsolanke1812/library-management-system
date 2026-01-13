package com.booktrack.librarymanagementsystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowerActivityDTO {

    private UUID borrowerId;
    private String name;
    private long totalBorrowed;
    private long overdueCount;
    private BigDecimal totalFines;


}
