package com.booktrack.librarymanagementsystem.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDTO {

    private UUID bookId;

    private String title;

    private String author;

    private String category;

    private int totalCopies;

    private int availableCopies;

    private boolean softDeleted;

}