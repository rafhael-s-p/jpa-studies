package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@DiscriminatorValue("slip")
@Entity
public class PaymentSlip extends Payment {

    @NotBlank
    @Column(name = "bar_code", length = 100)
    private String barCode;

    @NotNull
    @FutureOrPresent
    @Column(name = "due_date")
    private LocalDate dueDate;

}
