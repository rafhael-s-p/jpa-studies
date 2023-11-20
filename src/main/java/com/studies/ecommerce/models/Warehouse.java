package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_warehouse")
public class Warehouse {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    private Integer amount;
}
