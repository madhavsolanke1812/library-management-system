package com.booktrack.librarymanagementsystem.mapper;

import com.booktrack.librarymanagementsystem.dto.FinePolicyDTO;
import com.booktrack.librarymanagementsystem.entity.FinePolicy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinePolicyMapper {

    public static FinePolicyDTO toDto(FinePolicy fp) {
        if (fp == null) {
            return null;
        }
        return FinePolicyDTO.builder()
                .finePolicyId(fp.getFinePolicyId())
                .category(fp.getCategory())
                .finePerDay(fp.getFinePerDay())
                .build();
    }


    public static FinePolicy toEntity(FinePolicyDTO dto) {
        if (dto == null) {
            return null;
        }
        return FinePolicy.builder()
                .finePolicyId(dto.getFinePolicyId())
                .category(dto.getCategory())
                .finePerDay(dto.getFinePerDay())
                .build();

    }


}
