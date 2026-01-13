package com.booktrack.librarymanagementsystem.controller;

import com.booktrack.librarymanagementsystem.dto.BorrowRecordDTO;
import com.booktrack.librarymanagementsystem.dto.BorrowerCreateRequest;
import com.booktrack.librarymanagementsystem.dto.BorrowerDTO;
import com.booktrack.librarymanagementsystem.service.BorrowRecordService;
import com.booktrack.librarymanagementsystem.service.BorrowerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/borrowers")
@RequiredArgsConstructor
public class BorrowerController {

    private final BorrowerService borrowerService;
    private final BorrowRecordService borrowRecordService;

    @PostMapping
    public ResponseEntity<BorrowerDTO> register(@Valid @RequestBody BorrowerCreateRequest req) {
        return ResponseEntity.status(201).body(borrowerService.createBorrower(req));
    }

    @GetMapping("/{id}/records")
    public ResponseEntity<List<BorrowRecordDTO>> getHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(borrowRecordService.getBorrowHistoryByBorrower(id));
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<UUID>> overdueBorrowers() {
        return ResponseEntity.ok(borrowRecordService.listBorrowersWithOverdue());
    }
}
