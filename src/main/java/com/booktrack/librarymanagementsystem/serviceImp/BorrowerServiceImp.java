package com.booktrack.librarymanagementsystem.serviceImp;

import com.booktrack.librarymanagementsystem.dto.BorrowerCreateRequest;
import com.booktrack.librarymanagementsystem.dto.BorrowerDTO;
import com.booktrack.librarymanagementsystem.entity.Borrower;
import com.booktrack.librarymanagementsystem.exception.NotFoundException;
import com.booktrack.librarymanagementsystem.mapper.BorrowerMapper;
import com.booktrack.librarymanagementsystem.repository.BorrowerRepository;
import com.booktrack.librarymanagementsystem.service.BorrowerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BorrowerServiceImp implements BorrowerService {
    private final BorrowerRepository borrowerRepository;

    @Override
    @Transactional
    public BorrowerDTO createBorrower(BorrowerCreateRequest request) {
        Borrower borrower = Borrower.builder()
                .name(request.getName())
                .email(request.getEmail())
                .memberShipType(request.getMemberShipType())
                .maxBorrowLimit(request.getMemberShipType() == null ? 2 : (request.getMemberShipType().name().equals("BASIC") ? 2 : 5))
                .build();
        borrower = borrowerRepository.save(borrower);
        return BorrowerMapper.toDto(borrower);
    }

    @Override
    public BorrowerDTO getById(java.util.UUID id) {
        Borrower b = borrowerRepository.findById(id).orElseThrow(() -> new NotFoundException("Borrower not found"));
        return BorrowerMapper.toDto(b);
    }

}

