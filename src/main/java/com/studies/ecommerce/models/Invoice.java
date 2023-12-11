package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tab_invoice")
public class Invoice extends BaseEntity {

    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_invoice_order"))
    private Order order;

    @Column(nullable = false)
    @Lob
    private byte[] xml;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "emission_date", nullable = false)
    private Date emissionDate;

}
