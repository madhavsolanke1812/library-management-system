package com.booktrack.librarymanagementsystem.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopBorrowedDTO {

    private UUID bookId;
    private String title;
    private String author;
    private long borrowCount;
}

