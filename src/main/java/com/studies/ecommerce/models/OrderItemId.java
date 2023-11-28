package com.studies.ecommerce.models;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable {

    @EqualsAndHashCode.Include
    private Integer orderId;

    @EqualsAndHashCode.Include
    private Integer productId;

}
