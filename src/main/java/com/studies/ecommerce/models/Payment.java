package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@DiscriminatorColumn(name = "payment_type",
        discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table(name = "tab_payment")
public abstract class Payment extends BaseEntity {

    @NotNull
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_order"))
    private Order order;

    @NotNull
    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

}