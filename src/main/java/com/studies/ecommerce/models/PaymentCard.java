package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@DiscriminatorValue("card")
@Entity
public class PaymentCard extends Payment {

    @NotEmpty
    @Column(name = "card_number", length = 50)
    private String cardNumber;

}
