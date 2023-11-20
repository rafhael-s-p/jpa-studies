package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "card_payment")
public class CardPayment {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String number;
}
