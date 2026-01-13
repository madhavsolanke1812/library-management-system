package com.booktrack.librarymanagementsystem.dto;

import com.booktrack.librarymanagementsystem.entity.MemberShipType;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowerDTO {

    private UUID borrowerId;

    private String name;

    private String email;

    private MemberShipType memberShipType;

    /// Not stored, derived from other
    private int maxBorrowLimit;

}