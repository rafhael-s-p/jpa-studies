package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@DiscriminatorValue("card")
@Entity
@Table(name = "tab_payment_card")
public class PaymentCard extends Payment {

    @Column(name = "card_number")
    private String cardNumber;

}
