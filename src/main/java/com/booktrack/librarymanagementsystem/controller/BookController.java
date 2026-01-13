package com.booktrack.librarymanagementsystem.controller;

import com.booktrack.librarymanagementsystem.dto.BookCreateRequest;
import com.booktrack.librarymanagementsystem.dto.BookDTO;
import com.booktrack.librarymanagementsystem.dto.BookUpdateRequest;
import com.booktrack.librarymanagementsystem.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookDTO> createOrIncrease(@Valid @RequestBody BookCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createOrIncrease(request));
    }

    @GetMapping
    public ResponseEntity<Page<BookDTO>> list(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Boolean available,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "title") String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(bookService.listBooks(category, available, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> get(@PathVariable UUID id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> update(@PathVariable UUID id, @Valid @RequestBody BookUpdateRequest req) {
        return ResponseEntity.ok(bookService.updateBook(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        bookService.softDelete(id);
        return ResponseEntity.noContent().build();
    }
}















