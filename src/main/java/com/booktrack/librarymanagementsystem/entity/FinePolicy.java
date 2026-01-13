package com.booktrack.librarymanagementsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "fine_policy")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinePolicy {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "fine_policy_id", updatable = false, nullable = false)
    private UUID finePolicyId;

    @NotBlank
    private String category;

    @NotNull
    private BigDecimal finePerDay;

}
