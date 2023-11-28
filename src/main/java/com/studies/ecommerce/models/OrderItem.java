package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(OrderItemId.class)
@Entity
@Table(name = "tab_order_item")
public class OrderItem {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "order_id")
    private Integer orderId;

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "product_id")
    private Integer productId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    private Integer amount;
}
