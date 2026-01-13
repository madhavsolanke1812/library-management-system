package com.booktrack.librarymanagementsystem.service;

import com.booktrack.librarymanagementsystem.dto.AvailabilitySummaryDTO;
import com.booktrack.librarymanagementsystem.dto.BookCreateRequest;
import com.booktrack.librarymanagementsystem.dto.BookDTO;
import com.booktrack.librarymanagementsystem.dto.BookUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BookService {

    BookDTO createOrIncrease(BookCreateRequest request);

    Page<BookDTO> listBooks(String category, Boolean available, Pageable pageable);

    BookDTO updateBook(UUID id, BookUpdateRequest req);

    void softDelete(UUID id);

    BookDTO getById(UUID id);

    List<BookDTO> findSimilarBooks(java.util.UUID bookId);

    List<AvailabilitySummaryDTO> availabilitySummary();

}
