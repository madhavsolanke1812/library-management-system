package com.booktrack.librarymanagementsystem.service;

import com.booktrack.librarymanagementsystem.dto.BorrowRecordDTO;
import com.booktrack.librarymanagementsystem.dto.BorrowRequest;
import com.booktrack.librarymanagementsystem.dto.ReturnRequest;

import java.util.List;
import java.util.UUID;

public interface BorrowRecordService {

    BorrowRecordDTO borrow(BorrowRequest req);

    BorrowRecordDTO returnBook(ReturnRequest req);

    List<BorrowRecordDTO> listActiveRecords();

    List<BorrowRecordDTO> getBorrowHistoryByBorrower(UUID borrowerId);

    List<UUID> listBorrowersWithOverdue();

}
