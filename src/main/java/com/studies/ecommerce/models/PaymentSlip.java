package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Getter
@Setter
@DiscriminatorValue("slip")
@Entity
public class PaymentSlip extends Payment {

    @Column(name = "bar_code", length = 100)
    private String barCode;

    @Column(name = "due_date")
    private LocalDate dueDate;

}
