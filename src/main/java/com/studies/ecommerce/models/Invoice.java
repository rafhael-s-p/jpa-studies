package com.studies.ecommerce.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "tab_invoice")
public class Invoice {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "order_id")
    private Integer id;

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id")
    private Order order;

    @Lob
    private byte[] xml;

    @Column(name = "emission_date")
    private Date emissionDate;
}
