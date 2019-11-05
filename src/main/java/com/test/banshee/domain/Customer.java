package com.test.banshee.domain;

import com.test.banshee.converter.NitConverter;
import com.test.banshee.validator.Nit;
import com.test.banshee.validator.Phone;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString(exclude = {"city", "visits"})
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Nit
    @Convert(converter = NitConverter.class)
    private String nit;

    @NotBlank
    private String fullName;

    @NotBlank
    private String address;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "city_id")
    private City city;

    @NotBlank
    @Phone
    private String phone;

    @NotNull
    private BigDecimal creditLimit;

    @NotNull
    private BigDecimal availableCredit;

    @NotNull
    private Double visitPercentage;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Visit> visits = new ArrayList<>();

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
        final Customer other = (Customer) obj;
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return 31;
    }

}
