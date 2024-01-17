package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Entity
@Table(name = "tab_warehouse")
public class Warehouse extends BaseEntity {

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_warehouse_product"))
    private Product product;

    @PositiveOrZero
    @NotNull
    private Integer amount;

}
