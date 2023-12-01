package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "tab_payment_card")
public class PaymentCard extends Payment {

    @Column(name = "card_number")
    private String cardNumber;

}