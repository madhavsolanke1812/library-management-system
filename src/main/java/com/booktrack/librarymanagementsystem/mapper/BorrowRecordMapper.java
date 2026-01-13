package com.booktrack.librarymanagementsystem.mapper;

import com.booktrack.librarymanagementsystem.dto.BorrowRecordDTO;
import com.booktrack.librarymanagementsystem.entity.BorrowRecord;

public class BorrowRecordMapper {

    public static BorrowRecordDTO toDto(BorrowRecord r) {

        if (r == null) {
            return null;
        }

        return BorrowRecordDTO.builder()
                .borrowRecordId(r.getBorrowRecordId())
                .bookId(r.getBook().getBookId())
                .borrowerId(r.getBorrower().getBorrowerId())
                .borrowDate(r.getBorrowDate())
                .dueDate(r.getDueDate())
                .returnDate(r.getReturnDate())
                .fineAmount(r.getFineAmount())
                .overDueFlag(r.isOverDueFlag())
                .build();

    }

}
