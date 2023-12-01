package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@DiscriminatorValue("card")
@Entity
// @Table(name = "tab_payment_card") This annotation is ignored for InheritanceType.SINGLE_TABLE
public class PaymentCard extends Payment {

    @Column(name = "card_number")
    private String cardNumber;

}
