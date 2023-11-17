package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Invoice {

    @EqualsAndHashCode.Include
    @Id
    private Integer id;

    private Integer orderId;

    private String xml;

    private Date emissionDate;
}
