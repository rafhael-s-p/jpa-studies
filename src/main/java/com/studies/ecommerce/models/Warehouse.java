package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Warehouse {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private Integer productId;

    private Integer amount;
}
