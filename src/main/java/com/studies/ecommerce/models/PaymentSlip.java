package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tab_payment_slip")
public class PaymentSlip extends Payment {

    @Column(name = "bar_code")
    private String barCode;

}
