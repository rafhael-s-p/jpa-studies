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
// @Table(name = "tab_payment_slip") This annotation is ignored for InheritanceType.SINGLE_TABLE
public class PaymentSlip extends Payment {

    @Column(name = "bar_code")
    private String barCode;

}
