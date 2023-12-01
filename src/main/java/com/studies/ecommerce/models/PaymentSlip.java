package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@DiscriminatorValue("slip")
@Entity
public class PaymentSlip extends Payment {

    @Column(name = "bar_code")
    private String barCode;

}
