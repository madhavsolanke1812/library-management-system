package com.booktrack.librarymanagementsystem.scheduler;

import com.booktrack.librarymanagementsystem.entity.BorrowRecord;
import com.booktrack.librarymanagementsystem.repository.BorrowRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OverDueScheduler {
    private final BorrowRecordRepository borrowRecordRepository;

    //At MidNight 12:00
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void flagOverDue() {
        List<BorrowRecord> overdue = borrowRecordRepository.findOverdueRecords(LocalDate.now());
        overdue.forEach(r -> {
            if (!r.isOverDueFlag()) {
                r.setOverDueFlag(true);
            }
        });
        borrowRecordRepository.saveAll(overdue);
    }
}
