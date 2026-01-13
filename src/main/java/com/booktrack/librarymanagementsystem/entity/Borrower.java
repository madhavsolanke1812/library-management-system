package com.booktrack.librarymanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Table(name = "borrowers")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Borrower {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "borrower_id", updatable = false, nullable = false)
    private UUID borrowerId;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberShipType memberShipType;

    private int maxBorrowLimit;


}
