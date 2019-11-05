package com.test.banshee.domain;

import lombok.EqualsAndHashCode;
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
@ToString(exclude = "country")
@Immutable
@Table(name = "state")
public class State {

    @Id
    private Short id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "country_id")
    private Country country;

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
        final State other = (State) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
