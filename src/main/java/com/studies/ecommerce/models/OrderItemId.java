package com.studies.ecommerce.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderItemId implements Serializable {

    @EqualsAndHashCode.Include
    @Column(name = "order_id")
    private Integer orderId;

    @EqualsAndHashCode.Include
    @Column(name = "product_id")
    private Integer productId;

}
