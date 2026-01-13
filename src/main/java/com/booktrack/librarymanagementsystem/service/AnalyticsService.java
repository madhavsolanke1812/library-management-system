package com.booktrack.librarymanagementsystem.service;

import com.booktrack.librarymanagementsystem.dto.AvailabilitySummaryDTO;
import com.booktrack.librarymanagementsystem.dto.BorrowerActivityDTO;
import com.booktrack.librarymanagementsystem.dto.TopBorrowedDTO;

import java.util.List;

public interface AnalyticsService {
    List<TopBorrowedDTO> topBorrowedBooks(int limit);

    List<BorrowerActivityDTO> borrowerActivity();

    List<AvailabilitySummaryDTO> availabilitySummary();


}
