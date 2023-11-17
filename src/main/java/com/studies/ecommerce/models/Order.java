package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Order {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private LocalDateTime orderData;

    private LocalDateTime completionDate;

    private Integer invoiceId;

    private BigDecimal total;

    private OrderStatus status;
}
