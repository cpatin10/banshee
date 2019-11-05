package com.test.banshee.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@ToString
@Immutable
@Table(name = "country")
public class Country {

    @Id
    private Short id;

    @NotBlank
    private String name;

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
        final Country other = (Country) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
