package com.booktrack.librarymanagementsystem.repository;

import com.booktrack.librarymanagementsystem.entity.BorrowRecord;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, UUID> {

    List<BorrowRecord> findByBorrowerBorrowerId(UUID borrowerId);

    @Query("select br from BorrowRecord br where br.returnDate is null")
    List<BorrowRecord> findActiveRecords();

    @Query("select br from BorrowRecord br where br.returnDate is null and br.dueDate < :today")
    List<BorrowRecord> findOverdueRecords(@Param("today") LocalDate today);

    @Query("select br.book.bookId as bookId, br.book.title as title, count(br) as cnt " +
            "from BorrowRecord br group by br.book.bookId, br.book.title order by cnt desc")
    List<Object[]> countBorrowedPerBook(Pageable pageable);

    @Query("select br.borrower.borrowerId as borrowerId, br.borrower.name as name, count(br) as totalBorrowed, " +
            "sum(case when br.returnDate is null and br.dueDate < :today then 1 else 0 end) as overdueCount, " +
            "coalesce(sum(br.fineAmount),0) as totalFines " +
            "from BorrowRecord br group by br.borrower.borrowerId, br.borrower.name")
    List<Object[]> borrowerActivity(@Param("today") LocalDate today);

}
