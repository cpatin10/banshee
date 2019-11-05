package com.test.banshee.dto.visit;

import com.test.banshee.dto.SalesRepresentativeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetVisitDTO {

    private long id;

    private Instant date;

    private long net;

    private BigDecimal visitTotal;

    private String description;

    private SalesRepresentativeDTO salesRepresentative;

}
