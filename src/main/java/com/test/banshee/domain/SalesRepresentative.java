package com.test.banshee.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@ToString
@Immutable
@Table(name = "sales_representative")
public class SalesRepresentative {

    @Id
    private Long id;

    @NotBlank
    private String fullName;

    @NotBlank
    @NaturalId
    private String identificationNumber;

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
        final SalesRepresentative other = (SalesRepresentative) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
