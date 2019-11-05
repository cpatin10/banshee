package com.test.banshee.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@ToString(exclude = "state")
@Immutable
@Table(name = "city")
public class City {

    @Id
    private Short id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "state_id")
    private State state;

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
        final City other = (City) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
