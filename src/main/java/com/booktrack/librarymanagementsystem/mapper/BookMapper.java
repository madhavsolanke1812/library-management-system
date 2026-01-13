package com.booktrack.librarymanagementsystem.mapper;

import com.booktrack.librarymanagementsystem.dto.BookDTO;
import com.booktrack.librarymanagementsystem.entity.Book;

public class BookMapper {

    /// Book to Dto
    public static BookDTO toDto(Book b) {
        if (b == null) {
            return null;
        }
        return BookDTO.builder()
                .bookId(b.getBookId())
                .title(b.getTitle())
                .author(b.getAuthor())
                .category(b.getCategory())
                .availableCopies(b.getAvailableCopies())
                .totalCopies(b.getTotalCopies())
                .build();

    }

    /// Dto to Book Entity
    public static Book toEntity(BookDTO dto) {
        if (dto == null) {
            return null;
        }
        return Book.builder()
                .bookId(dto.getBookId())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .category(dto.getCategory())
                .totalCopies(dto.getTotalCopies())
                .availableCopies(dto.getAvailableCopies())
                .softDeleted(dto.isSoftDeleted())
                .build();
    }

}
