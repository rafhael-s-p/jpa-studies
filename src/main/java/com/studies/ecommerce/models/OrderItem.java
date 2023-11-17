package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class OrderItem {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private Integer orderId;

    private Integer productId;

    private BigDecimal productPrice;

    private Integer amount;
}
