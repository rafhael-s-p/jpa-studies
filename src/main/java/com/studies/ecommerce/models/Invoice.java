package com.studies.ecommerce.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tab_invoice")
public class Invoice extends BaseEntity {

    @NotNull
    @MapsId
    @OneToOne(optional = false)
    @JoinColumn(name = "order_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_invoice_order"))
    private Order order;

    @NotEmpty
    @Column(nullable = false)
    @Lob
    @Type(type="org.hibernate.type.BinaryType")
    private byte[] xml;

    @PastOrPresent
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "emission_date", nullable = false)
    private Date emissionDate;

}
