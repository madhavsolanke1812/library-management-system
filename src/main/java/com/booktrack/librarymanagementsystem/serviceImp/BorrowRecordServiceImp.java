package com.booktrack.librarymanagementsystem.serviceImp;

import com.booktrack.librarymanagementsystem.dto.BorrowRecordDTO;
import com.booktrack.librarymanagementsystem.dto.BorrowRequest;
import com.booktrack.librarymanagementsystem.dto.ReturnRequest;
import com.booktrack.librarymanagementsystem.entity.*;
import com.booktrack.librarymanagementsystem.exception.BadRequestException;
import com.booktrack.librarymanagementsystem.exception.NotFoundException;
import com.booktrack.librarymanagementsystem.mapper.BorrowRecordMapper;
import com.booktrack.librarymanagementsystem.repository.BookRepository;
import com.booktrack.librarymanagementsystem.repository.BorrowRecordRepository;
import com.booktrack.librarymanagementsystem.repository.BorrowerRepository;
import com.booktrack.librarymanagementsystem.repository.FinePolicyRepository;
import com.booktrack.librarymanagementsystem.service.BorrowRecordService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowRecordServiceImp implements BorrowRecordService {
    private static final BigDecimal DEFAULT_FINE_PER_DAY = new BigDecimal("5");
    private final BookRepository bookRepository;
    private final BorrowerRepository borrowerRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final FinePolicyRepository finePolicyRepository;

    @Override
    @Transactional
    public BorrowRecordDTO borrow(BorrowRequest req) {
        Borrower borrower = borrowerRepository.findById(req.getBorrowerId())
                .orElseThrow(() -> new NotFoundException("Borrower not found"));

        // calculate active borrows for borrower
        long activeCount = borrowRecordRepository.findActiveRecords().stream()
                .filter(br -> br.getBorrower().getBorrowerId()
                        .equals(borrower.getBorrowerId())).count();

        int maxLimit = borrower.getMemberShipType() == null ? 2 :
                (borrower.getMemberShipType() == MemberShipType.BASIC ? 2 : 5);

        if (activeCount >= maxLimit) throw new BadRequestException("Borrow limit exceeded");

        // lock book row
        Book book = bookRepository.findByIdForUpdate(req.getBookId())
                .orElseThrow(() -> new NotFoundException("Book not available"));

        if (book.getAvailableCopies() <= 0)
            throw new BadRequestException("No copies available");

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BorrowRecord record = BorrowRecord.builder()
                .book(book)
                .borrower(borrower)
                .borrowDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(14))
                .fineAmount(BigDecimal.ZERO)
                .overDueFlag(false)
                .build();

        record = borrowRecordRepository.save(record);
        return BorrowRecordMapper.toDto(record);
    }

    @Override
    @Transactional
    public BorrowRecordDTO returnBook(ReturnRequest req) {
        BorrowRecord record = borrowRecordRepository.findById(req.getBorrowRecordId())
                .orElseThrow(() -> new NotFoundException("Borrow record not Found"));

        if (record.getReturnDate() != null)
            throw new BadRequestException("Book already Returned");

        record.setReturnDate(LocalDate.now());

        // finePolicy calculated here
        if (record.getReturnDate().isAfter(record.getDueDate())) {
            long daysLate = ChronoUnit.DAYS.between(record.getDueDate(), record.getReturnDate());

            BigDecimal finePerDay = finePolicyRepository.findByCategory(record.getBook()
                    .getCategory()).map(FinePolicy::getFinePerDay).orElse(DEFAULT_FINE_PER_DAY);

            BigDecimal fine = finePerDay.multiply(BigDecimal.valueOf(daysLate));
            record.setFineAmount(fine);
        }

        // update the book copies
        Book book = bookRepository.findByIdForUpdate(record.getBook().getBookId())
                .orElseThrow(() -> new NotFoundException("Book not Found"));

        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        record = borrowRecordRepository.save(record);
        return BorrowRecordMapper.toDto(record);
    }

    @Override
    public List<BorrowRecordDTO> listActiveRecords() {
        return borrowRecordRepository.findActiveRecords()
                .stream()
                .map(BorrowRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BorrowRecordDTO> getBorrowHistoryByBorrower(UUID borrowerId) {
        return borrowRecordRepository.findByBorrowerBorrowerId(borrowerId)
                .stream()
                .map(BorrowRecordMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<UUID> listBorrowersWithOverdue() {
        return borrowRecordRepository.findOverdueRecords(LocalDate.now())
                .stream()
                .map(br -> br.getBorrower().getBorrowerId())
                .distinct()
                .collect(Collectors.toList());
    }

}
