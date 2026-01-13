package com.booktrack.librarymanagementsystem.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowRecordDTO {

    private UUID borrowRecordId;

    private UUID bookId;

    private UUID borrowerId;

    private LocalDate borrowDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private BigDecimal fineAmount;

    private boolean overDueFlag;
}

