package com.test.banshee.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Getter
@Setter
@ToString(exclude = {"customer", "salesRepresentative"})
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @NotNull
    private Instant date;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "sales_representative_id")
    private SalesRepresentative salesRepresentative;

    @NotNull
    private Long net;

    @NotNull
    private BigDecimal visitTotal;

    @NotBlank
    private String description;

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Visit other = (Visit) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
