package com.booktrack.librarymanagementsystem.controller;

import com.booktrack.librarymanagementsystem.dto.BorrowRecordDTO;
import com.booktrack.librarymanagementsystem.dto.BorrowRequest;
import com.booktrack.librarymanagementsystem.dto.ReturnRequest;
import com.booktrack.librarymanagementsystem.service.BorrowRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;

    @PostMapping("/borrow")
    public ResponseEntity<BorrowRecordDTO> borrow(@Valid @RequestBody BorrowRequest req) {
        return ResponseEntity.status(201).body(borrowRecordService.borrow(req));
    }

    @PostMapping("/return")
    public ResponseEntity<BorrowRecordDTO> returnBook(@Valid @RequestBody ReturnRequest req) {
        return ResponseEntity.ok(borrowRecordService.returnBook(req));
    }

    @GetMapping("/records/active")
    public ResponseEntity<List<BorrowRecordDTO>> activeRecords() {
        return ResponseEntity.ok(borrowRecordService.listActiveRecords());
    }
}
