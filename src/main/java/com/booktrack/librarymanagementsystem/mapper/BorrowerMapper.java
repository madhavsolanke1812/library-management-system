package com.booktrack.librarymanagementsystem.mapper;

import com.booktrack.librarymanagementsystem.dto.BorrowerDTO;
import com.booktrack.librarymanagementsystem.entity.Borrower;
import com.booktrack.librarymanagementsystem.entity.MemberShipType;

public class BorrowerMapper {

    public static BorrowerDTO toDto(Borrower b) {
        if (b == null) {
            return null;
        }

        int limit = getLimit(b.getMemberShipType());

        return BorrowerDTO.builder()
                .borrowerId(b.getBorrowerId())
                .name(b.getName())
                .email(b.getEmail())
                .memberShipType(b.getMemberShipType())
                .maxBorrowLimit(limit)
                .build();

    }

    private static int getLimit(MemberShipType type) {
        return type == MemberShipType.BASIC ? 2 : 5;

    }
}
