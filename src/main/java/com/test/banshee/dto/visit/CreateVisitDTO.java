package com.test.banshee.dto.visit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateVisitDTO {

    private long customerId;

    @NotNull
    private Instant date;

    @Min(0)
    private long net;

    @NotBlank
    private String description;

    @NotBlank
    private String salesRepresentativeIdNumber;

}
