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
public class Category {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private String name;

    private Integer categoryFatherId;
}
