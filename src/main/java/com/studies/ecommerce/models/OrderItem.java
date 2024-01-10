package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SqlResultSetMappings({
        @SqlResultSetMapping(name = "order_item-product.OrderItem-Product",
                entities = { @EntityResult(entityClass = OrderItem.class),
                        @EntityResult(entityClass = Product.class) })
})
@Entity
@Table(name = "tab_order_item")
public class OrderItem {

    @EmbeddedId
    private OrderItemId id;

    @MapsId("orderId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_item_order"))
    private Order order;

    @MapsId("productId")
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_order_item_product"))
    private Product product;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    @Column(nullable = false)
    private Integer amount;

}
