
package com.booktrack.librarymanagementsystem.dto;

import com.booktrack.librarymanagementsystem.entity.MemberShipType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowerCreateRequest {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    private MemberShipType memberShipType;

}
