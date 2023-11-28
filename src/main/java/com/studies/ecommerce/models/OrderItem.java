package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

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
