package com.booktrack.librarymanagementsystem.service;

import com.booktrack.librarymanagementsystem.dto.BorrowerCreateRequest;
import com.booktrack.librarymanagementsystem.dto.BorrowerDTO;

import java.util.UUID;

public interface BorrowerService {

    BorrowerDTO createBorrower(BorrowerCreateRequest request);

    BorrowerDTO getById(UUID id);
}
