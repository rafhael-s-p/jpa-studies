package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_order")
public class Order {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "completion_date")
    private LocalDateTime completionDate;

    @Column(name = "invoice_id")
    private Integer invoiceId;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Embedded
    @Column(name = "delivery_address")
    private Address deliveryAddress;

}
