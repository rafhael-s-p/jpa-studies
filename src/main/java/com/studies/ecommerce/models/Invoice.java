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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "order_id")
//    @JoinTable(name = "tab_order_invoice",
//        joinColumns = @JoinColumn(name = "invoice_id", unique = true),
//        inverseJoinColumns = @JoinColumn(name = "order_id", unique = true))
    private Order order;

    private String xml;

    @Column(name = "emission_date")
    private Date emissionDate;
}
