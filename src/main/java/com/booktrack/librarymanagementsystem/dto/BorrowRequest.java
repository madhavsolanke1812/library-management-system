package com.booktrack.librarymanagementsystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowRequest {

    @NotNull
    private UUID borrowerId;

    @NotNull
    private UUID bookId;

}
