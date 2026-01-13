package com.booktrack.librarymanagementsystem.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilitySummaryDTO {

    private String category;
    private long available;
    private long total;
}


