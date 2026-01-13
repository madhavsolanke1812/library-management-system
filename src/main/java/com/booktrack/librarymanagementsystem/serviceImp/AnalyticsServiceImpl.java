package com.booktrack.librarymanagementsystem.serviceImp;

import com.booktrack.librarymanagementsystem.dto.AvailabilitySummaryDTO;
import com.booktrack.librarymanagementsystem.dto.BorrowerActivityDTO;
import com.booktrack.librarymanagementsystem.dto.TopBorrowedDTO;
import com.booktrack.librarymanagementsystem.entity.Book;
import com.booktrack.librarymanagementsystem.repository.BookRepository;
import com.booktrack.librarymanagementsystem.repository.BorrowRecordRepository;
import com.booktrack.librarymanagementsystem.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsServiceImpl implements AnalyticsService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;

    @Override
    public List<TopBorrowedDTO> topBorrowedBooks(int limit) {
        List<Object[]> rows = borrowRecordRepository.countBorrowedPerBook(PageRequest.of(0, limit));
        return rows.stream().map(r -> TopBorrowedDTO.builder()
                .bookId((java.util.UUID) r[0])
                .title((String) r[1])
                .borrowCount(((Number) r[2]).longValue())
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<BorrowerActivityDTO> borrowerActivity() {
        List<Object[]> rows = borrowRecordRepository.borrowerActivity(LocalDate.now());
        return rows.stream().map(r -> BorrowerActivityDTO.builder()
                .borrowerId((java.util.UUID) r[0])
                .name((String) r[1])
                .totalBorrowed(((Number) r[2]).longValue())
                .overdueCount(((Number) r[3]).longValue())
                .totalFines((java.math.BigDecimal) r[4])
                .build()).collect(Collectors.toList());
    }

    @Override
    public List<AvailabilitySummaryDTO> availabilitySummary() {
        return bookRepository.findAll().stream()
                .filter(b -> !b.isSoftDeleted())
                .collect(Collectors.groupingBy(Book::getCategory))
                .entrySet().stream()
                .map(e -> {
                    long total = e.getValue().stream().mapToLong(Book::getTotalCopies).sum();
                    long available = e.getValue().stream().mapToLong(Book::getAvailableCopies).sum();
                    return new AvailabilitySummaryDTO(e.getKey(), available, total);
                })
                .collect(Collectors.toList());
    }


}
