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
public class Product {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private String name;

    private String description;

    private BigDecimal price;

}
