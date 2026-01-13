package com.booktrack.librarymanagementsystem.controller;

import com.booktrack.librarymanagementsystem.dto.AvailabilitySummaryDTO;
import com.booktrack.librarymanagementsystem.dto.BookDTO;
import com.booktrack.librarymanagementsystem.repository.BorrowRecordRepository;
import com.booktrack.librarymanagementsystem.service.AnalyticsService;
import com.booktrack.librarymanagementsystem.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final BookService bookService;
    private final AnalyticsService analyticsService;
    private final BorrowRecordRepository borrowRecordRepository;

    @GetMapping("/top-borrowed-books")
    public ResponseEntity<List<Map<String, Object>>> topBorrowed() {
        List<Object[]> rows = borrowRecordRepository.countBorrowedPerBook(PageRequest.of(0, 5));
        List<Map<String, Object>> result = rows.stream().map(r ->
        {
            Map<String, Object> m = new HashMap<>();
            m.put("bookId", r[0]);
            m.put("title", r[1]);
            m.put("count", ((Number) r[2]).longValue());
            return m;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/borrower-activity")
    public ResponseEntity<List<Map<String, Object>>> borrowerActivity() {
        List<Object[]> rows = borrowRecordRepository.borrowerActivity(LocalDate.now());
        List<Map<String, Object>> result = rows.stream().map(r ->
        {
            Map<String, Object> m = new HashMap<>();
            m.put("borrowerId", r[0]);
            m.put("name", r[1]);
            m.put("totalBorrowed", ((Number) r[2]).longValue());
            m.put("overdueCount", ((Number) r[3]).longValue());
            m.put("totalFines", r[4]);
            return m;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(result);
    }


    @GetMapping("/similar/{id}")
    public ResponseEntity<List<BookDTO>> similar(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(bookService.findSimilarBooks(id));
    }

    @GetMapping("/availability-summary")
    public ResponseEntity<List<AvailabilitySummaryDTO>> availabilitySummary() {
        return ResponseEntity.ok(analyticsService.availabilitySummary());
    }
}
